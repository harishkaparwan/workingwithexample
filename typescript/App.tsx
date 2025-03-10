import React from 'react';
import { Column } from '@tanstack/react-table';
import { TableProvider } from './TableContext';
import DataTable from './DataTable';

// Define the User type (same as before)
interface User {
  id: number;
  name: string;
  email: string;
  phone: string;
}

function App() {
  // Define columns for the User type
  const columns: Column<User>[] = [
    { Header: 'ID', accessor: 'id' as const },
    { Header: 'Name', accessor: 'name' as const },
    { Header: 'Email', accessor: 'email' as const },
    { Header: 'Phone', accessor: 'phone' as const },
  ];

  return (
    <TableProvider>
      <h1>Users Table (Server-Side Pagination)</h1>
      <DataTable<User>
        columns={columns}
        defaultPageSize={10} // Optional: Set default page size to 10
      />
    </TableProvider>
  );
}

export default App;