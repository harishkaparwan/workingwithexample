import React, { useEffect } from 'react';
import {
  useTable,
  usePagination,
  useSortBy,
  useGlobalFilter,
  Column,
  TableInstance,
} from '@tanstack/react-table';
import { useTableData } from './TableContext';

// Define the props for the DataTable component
interface DataTableProps<T extends object> {
  columns: Column<T>[];
  endpoint?: string; // Optional: API endpoint to fetch data
  defaultPageSize?: number; // Optional: Default page size
}

function DataTable<T extends object>({ columns, endpoint, defaultPageSize = 5 }: DataTableProps<T>) {
  const {
    data,
    loading,
    error,
    pageIndex,
    setPageIndex,
    pageSize,
    setPageSize,
    totalRecords,
    sortBy,
    setSortBy,
    filter,
    setFilter,
  } = useTableData<T>();

  // Set up the table with manual (server-side) pagination, sorting, and filtering
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    canPreviousPage,
    canNextPage,
    nextPage,
    previousPage,
    setPageSize: setTablePageSize,
    setGlobalFilter,
    state: { pageIndex: tablePageIndex, pageSize: tablePageSize, sortBy: tableSortBy },
  }: TableInstance<T> = useTable(
    {
      columns,
      data,
      manualPagination: true,
      manualSortBy: true,
      manualGlobalFilter: true,
      pageCount: Math.ceil(totalRecords / pageSize),
      initialState: { pageIndex, pageSize: defaultPageSize, sortBy },
    },
    usePagination,
    useSortBy,
    useGlobalFilter
  );

  // Sync context state with table state
  useEffect(() => {
    setPageIndex(tablePageIndex);
    setPageSize(tablePageSize);
    setSortBy(tableSortBy as { id: string; desc: boolean }[]);
    setGlobalFilter(filter);
  }, [tablePageIndex, tablePageSize, tableSortBy, filter, setPageIndex, setPageSize, setSortBy, setGlobalFilter]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      {/* Global Filter Input */}
      <div style={{ marginBottom: '16px' }}>
        <input
          value={filter || ''}
          onChange={(e) => setFilter(e.target.value)}
          placeholder="Search..."
          style={{ padding: '8px', width: '200px' }}
        />
      </div>

      {/* Table */}
      <table {...getTableProps()} style={{ borderCollapse: 'collapse', width: '100%' }}>
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column) => (
                <th
                  {...column.getHeaderProps(column.getSortByToggleProps())}
                  style={{ border: '1px solid black', padding: '8px', background: '#f0f0f0' }}
                >
                  {column.render('Header')}
                  <span>{column.isSorted ? (column.isSortedDesc ? ' 🔽' : ' 🔼') : ''}</span>
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row) => {
            prepareRow(row);
            return (
              <tr {...row.getRowProps()}>
                {row.cells.map((cell) => (
                  <td
                    {...cell.getCellProps()}
                    style={{ border: '1px solid black', padding: '8px' }}
                  >
                    {cell.render('Cell')}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </table>

      {/* Pagination Controls */}
      <div style={{ marginTop: '16px' }}>
        <button onClick={() => previousPage()} disabled={!canPreviousPage}>
          Previous
        </button>
        <button onClick={() => nextPage()} disabled={!canNextPage}>
          Next
        </button>
        <span style={{ marginLeft: '16px' }}>
          Page {tablePageIndex + 1} of {Math.ceil(totalRecords / pageSize)}
        </span>
        <select
          value={tablePageSize}
          onChange={(e) => setTablePageSize(Number(e.target.value))}
          style={{ marginLeft: '16px' }}
        >
          {[5, 10, 20].map((size) => (
            <option key={size} value={size}>
              Show {size}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
}

export default DataTable;