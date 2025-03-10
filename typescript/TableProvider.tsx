import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import debounce from 'lodash.debounce';

interface TableContextValue<T> {
  data: T[];
  loading: boolean;
  error: string | null;
  pageIndex: number;
  setPageIndex: React.Dispatch<React.SetStateAction<number>>;
  pageSize: number;
  setPageSize: React.Dispatch<React.SetStateAction<number>>;
  totalRecords: number;
  sortBy: { id: string; desc: boolean }[];
  setSortBy: React.Dispatch<React.SetStateAction<{ id: string; desc: boolean }[]>>;
  filter: string;
  setFilter: React.Dispatch<React.SetStateAction<string>>;
}

const TableContext = createContext<TableContextValue<any> | undefined>(undefined);

interface TableProviderProps {
  children: React.ReactNode;
  endpoint?: string; // Add endpoint as a prop
}

export function TableProvider<T>({ children, endpoint }: TableProviderProps) {
  const [data, setData] = useState<T[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [pageIndex, setPageIndex] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalRecords, setTotalRecords] = useState(0);
  const [sortBy, setSortBy] = useState<{ id: string; desc: boolean }[]>([]);
  const [filter, setFilter] = useState('');

  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      const sortParam =
        sortBy.length > 0 ? `&_sort=${sortBy[0].id}&_order=${sortBy[0].desc ? 'desc' : 'asc'}` : '';
      const filterParam = filter ? `&q=${encodeURIComponent(filter)}` : '';
      const url = endpoint || 'https://jsonplaceholder.typicode.com/users';
      const response = await fetch(
        `${url}?_page=${pageIndex + 1}&_limit=${pageSize}${sortParam}${filterParam}`
      );
      if (!response.ok) throw new Error('Failed to fetch data');

      const total = response.headers.get('x-total-count') || 100;
      setTotalRecords(parseInt(total, 10));

      const result = await response.json();
      setData(result as T[]);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    } finally {
      setLoading(false);
    }
  }, [pageIndex, pageSize, sortBy, filter, endpoint]);

  const debouncedFetchData = debounce(fetchData, 300);

  useEffect(() => {
    debouncedFetchData();
    return () => {
      debouncedFetchData.cancel();
    };
  }, [debouncedFetchData]);

  return (
    <TableContext.Provider
      value={{
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
      }}
    >
      {children}
    </TableContext.Provider>
  );
}

export function useTableData<T>(): TableContextValue<T> {
  const context = useContext(TableContext);
  if (!context) {
    throw new Error('useTableData must be used within a TableProvider');
  }
  return context as TableContextValue<T>;
}