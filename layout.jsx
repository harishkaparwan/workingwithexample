import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Container, Box, Menu, MenuItem, Button } from '@mui/material';

const Layout = ({ children }) => {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            My App
          </Typography>
          <Button color="inherit" onClick={handleMenuOpen}>
            Menu
          </Button>
          <Menu
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleMenuClose}
          >
            <MenuItem component={Link} to="/" onClick={handleMenuClose}>Home</MenuItem>
            <MenuItem component={Link} to="/about" onClick={handleMenuClose}>About</MenuItem>
            <MenuItem component={Link} to="/contact" onClick={handleMenuClose}>Contact</MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>

      <Container component="main" sx={{ flexGrow: 1, mt: 2, mb: 2 }}>
        {children}
      </Container>

      <Box component="footer" sx={{ py: 2, px: 2, mt: 'auto', backgroundColor: 'primary.main', color: 'white' }}>
        <Typography variant="body2" align="center">
          © 2024 My App. All rights reserved.
        </Typography>
      </Box>
    </Box>
  );
};

export default Layout;
