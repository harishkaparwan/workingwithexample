import React, { useEffect, useState, useCallback, useMemo } from 'react';
import { useDebounce } from 'react-use'; // For search term debouncing (optional)
import debounce from 'lodash/debounce'; // Install via npm: npm install lodash
import {
  CustomColumn,
  DynamicColumnConfig,
} from '../../../Common/OfferDatatable/Column/CustomColumn';
import DataTableComp from '../../../Common/OfferDatatable/DataTableComp';
import { DataTableCompProps } from '../../../Common/OfferDatatable/DataTableCompProps';
import { DataTablePropTypes } from '../../../Common/OfferDatatable/DataTableTypes';
import { ToolbarConfig } from '../../../Common/OfferDatatable/Header/ToolbarControls';
import { OfferDataListKeyType, OfferTableColumnDisplay } from '../../../Common/OfferDatatable/mocks/fakeOfferData';
import CloneOfferModal from './CloneOfferModal';
import { FlagFilled, WarningFilled } from '@backyard/icons'; // Adjust import as needed

// TableContext setup
const TableContext = React.createContext<any>(null);

const TableProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [dataList, setDataList] = useState<OfferDataListKeyType[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [page, setPage] = useState(1);
  const [limit, setLimit] = useState(10);
  const [totalCount, setTotalCount] = useState(0);

  return (
    <TableContext.Provider
      value={{ dataList, setDataList, searchTerm, setSearchTerm, page, setPage, limit, setLimit, totalCount, setTotalCount }}
    >
      {children}
    </TableContext.Provider>
  );
};

// Props for OfferTable to accept custom columns
interface OfferTableProps {
  columns: OfferTableColumnDisplay[]; // Generic column configuration
  dataComponent: string; // e.g., 'Past', 'Future', or custom identifier
  apiEndpoint?: string; // Optional custom API endpoint
}

const OfferTable: React.FC<OfferTableProps> = ({ columns, dataComponent, apiEndpoint = 'http://localhost:3000/offers' }) => {
  const [selectedIds, setSelectedIds] = useState<Record<string, boolean>>({});
  const [selectedRows, setSelectedRows] = useState<any[]>([]);
  const [openCloneModal, setOpenCloneModal] = useState(false);
  const [modalState, setModalState] = useState({
    delete: false,
    deactivate: false,
    cancel: false,
  });

  // Access context
  const {
    dataList,
    setDataList,
    searchTerm,
    setSearchTerm,
    page,
    setPage,
    limit,
    setLimit,
    totalCount,
    setTotalCount,
  } = React.useContext(TableContext);

  // Debounced fetchData using lodash.debounce
  const fetchData = useCallback(
    debounce(async (search: string, pageNum: number, pageLimit: number) => {
      try {
        const response = await fetch(
          `${apiEndpoint}?search=${search}&page=${pageNum}&limit=${pageLimit}`,
        );
        const { data, totalCount: serverTotalCount } = await response.json();
        setDataList(data);
        setTotalCount(serverTotalCount);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }, 300), // 300ms debounce delay
    [apiEndpoint, setDataList, setTotalCount], // Dependencies for debounce
  );

  // Trigger fetchData when searchTerm, page, or limit changes
  useEffect(() => {
    fetchData(searchTerm, page, limit);
    return () => {
      fetchData.cancel(); // Cancel pending debounced calls on unmount or dependency change
    };
  }, [searchTerm, page, limit, fetchData]);

  // Modal and action handlers
  const handleModalState = (type: string, state: boolean) =>
    setModalState((prevState) => ({ ...prevState, [type]: state }));

  const handleAdd = () => console.log('handleAdd called');
  const deleteAdd = () => console.log('deleteAdd called');
  const handleModalClose = () => setOpenCloneModal(false);
  const cloneAdd = () => {
    setOpenCloneModal(true);
    console.log('cloneAdd called');
  };
  const handleSubmit = () => {
    console.log('Submit action triggered');
    setOpenCloneModal(false);
  };

  const handleSelect = ({ ids, rows }: { ids: Record<string, boolean>; rows: any[] }) => {
    console.log('Parent onSelect called with:', ids, rows);
    setSelectedIds(ids);
    setSelectedRows(rows);
  };

  const isDisabled = Object.keys(selectedIds).length === 0;
  const isButtonEnabled = (key: string, expectedValue: boolean): boolean =>
    selectedRows[0]?.values[key] === expectedValue;

  const onDeleteSubmit = () => {
    console.log('Deleting selected rows');
    handleModalState('delete', true);
  };
  const onDeactivateSubmit = () => {
    console.log('Deactivating selected rows');
    handleModalState('deactivate', true);
  };
  const onCancel = () => {
    console.log('Canceling selected rows');
    handleModalState('cancel', true);
  };

  // Toolbar configuration
  const toolbarConfig: ToolbarConfig = {
    buttons: [
      { label: 'Cancel', variant: 'secondary', disabled: isDisabled, size: 'small', onClick: onCancel },
      {
        label: 'Top Offer',
        variant: 'secondary',
        size: 'small',
        disabled: isButtonEnabled('topOffer', true),
        onClick: handleAdd,
      },
      { label: 'Clone', variant: 'secondary', size: 'small', disabled: isDisabled, onClick: cloneAdd },
      { label: 'Approve', variant: 'secondary', size: 'small', disabled: isDisabled, onClick: onDeleteSubmit },
      { label: 'Deactivate', variant: 'secondary', size: 'small', disabled: isDisabled, onClick: onDeactivateSubmit },
      { label: 'Delete', variant: 'secondary', size: 'small', disabled: isDisabled, onClick: onDeleteSubmit },
    ],
  };

  // Dynamic column configuration
  const dynamicColumnConfig: DynamicColumnConfig = {
    topOffer: {
      component: FlagFilled,
      dynamicProps: (value: any) => ({ color: value ? '#4682B4' : '' }),
    },
    alert: {
      component: WarningFilled,
      dynamicProps: (value: any) => ({ color: value ? '#467fb4' : '' }),
    },
  };

  // Memoize styledColumns using useMemo
  const styledColumns = useMemo(() => {
    return CustomColumn(columns, dynamicColumnConfig);
  }, [columns, dynamicColumnConfig]);

  // Modal operations configuration
  const modalWithOperationConfig = [
    { state: modalState.delete, handle: handleModalState.bind(null, 'delete'), submit: onDeleteSubmit, type: 'Delete' },
    {
      state: modalState.deactivate,
      handle: handleModalState.bind(null, 'deactivate'),
      submit: onDeactivateSubmit,
      type: 'Deactivate',
    },
    { state: modalState.cancel, handle: handleModalState.bind(null, 'cancel'), submit: onCancel, type: 'Cancel' },
  ];

  // DataTable props
  const dataTableProps: DataTableCompProps = {
    dataComponent,
    dataList,
    styledColumns,
    showToolbar: true,
    enableSortBy: true,
    enableSearch: false, // Disabled to manage search externally
    enableFilters: false, // Disabled for server-side filtering
    enableZebraStripes: true,
    pageSize: limit, // Reflects server-side limit
    onSelect: handleSelect,
    toolbarConfig,
    modalWithOperationConfig,
  };

  return (
    <div style={{ padding: '20px' }}>
      {/* Search Input */}
      <input
        type="text"
        placeholder="Search offers..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{ marginBottom: '10px', padding: '5px', width: '200px' }}
      />

      {/* Pagination Controls */}
      <div style={{ marginBottom: '10px' }}>
        <button
          disabled={page === 1}
          onClick={() => setPage(page - 1)}
          style={{ marginRight: '10px' }}
        >
          Previous
        </button>
        <span>Page {page}</span>
        <button
          disabled={page * limit >= totalCount}
          onClick={() => setPage(page + 1)}
          style={{ marginLeft: '10px', marginRight: '10px' }}
        >
          Next
        </button>
        <select
          value={limit}
          onChange={(e) => setLimit(parseInt(e.target.value))}
          style={{ padding: '5px' }}
        >
          <option value="10">10 per page</option>
          <option value="20">20 per page</option>
          <option value="50">50 per page</option>
        </select>
      </div>

      {/* Clone Modal */}
      <CloneOfferModal open={openCloneModal} handleModal={handleModalClose} onSubmit={handleSubmit} />

      {/* Data Table */}
      <DataTableComp {...dataTableProps} />
    </div>
  );
};

// Wrap OfferTable with TableProvider
const WrappedOfferTable: React.FC<OfferTableProps> = (props) => (
  <TableProvider>
    <OfferTable {...props} />
  </TableProvider>
);

export default WrappedOfferTable;
