import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import {debounce} from 'lodash';

// Define the shape of the context value as a generic type
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

// Create the Table Context with a generic type and default value of undefined
const TableContext = createContext<TableContextValue<any> | undefined>(undefined);

export function TableProvider<T>({ children }: { children: React.ReactNode }) {
  const [data, setData] = useState<T[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [pageIndex, setPageIndex] = useState(0); // Current page (0-based index)
  const [pageSize, setPageSize] = useState(5); // Rows per page
  const [totalRecords, setTotalRecords] = useState(0); // Total number of records
  const [sortBy, setSortBy] = useState<{ id: string; desc: boolean }[]>([]);
  const [filter, setFilter] = useState('');

  // Memoize the fetchData function with useCallback
  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      const sortParam =
        sortBy.length > 0 ? `&_sort=${sortBy[0].id}&_order=${sortBy[0].desc ? 'desc' : 'asc'}` : '';
      const filterParam = filter ? `&q=${encodeURIComponent(filter)}` : '';
      const response = await fetch(
        `https://jsonplaceholder.typicode.com/users?_page=${pageIndex + 1}&_limit=${pageSize}${sortParam}${filterParam}`
      );
      if (!response.ok) throw new Error('Failed to fetch data');

      const total = response.headers.get('x-total-count') || 100; // JSONPlaceholder provides this
      setTotalRecords(parseInt(total, 10));

      const result = await response.json();
      setData(result as T[]);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    } finally {
      setLoading(false);
    }
  }, [pageIndex, pageSize, sortBy, filter]);

  // Debounce the fetchData function (300ms delay)
  const debouncedFetchData = debounce(fetchData, 300);

  // Trigger the debounced fetch when dependencies change
  useEffect(() => {
    debouncedFetchData();
    // Cleanup debounce on unmount to prevent memory leaks
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

// Custom hook to use the Table Context with type safety
export function useTableData<T>(): TableContextValue<T> {
  const context = useContext(TableContext);
  if (!context) {
    throw new Error('useTableData must be used within a TableProvider');
  }
  return context as TableContextValue<T>;
}