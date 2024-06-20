import React, { useState } from 'react';
import { Button, TextField, MenuItem, Select, InputLabel, FormControl, Typography, Box } from '@mui/material';
import { CloudUpload as CloudUploadIcon } from '@mui/icons-material';

const MyForm = () => {
  const [selectedOption, setSelectedOption] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);

  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);
  };

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle form submission
    console.log('Selected Option:', selectedOption);
    console.log('Selected File:', selectedFile);
  };

  return (
    <Box sx={{ maxWidth: 500, mx: 'auto', mt: 5, p: 3, border: '1px solid #ccc', borderRadius: 1 }}>
      <Typography variant="h5" component="h2" gutterBottom>
        My Form
      </Typography>
      <form onSubmit={handleSubmit}>
        <FormControl fullWidth margin="normal">
          <InputLabel id="select-label">Select Option</InputLabel>
          <Select
            labelId="select-label"
            value={selectedOption}
            onChange={handleSelectChange}
            label="Select Option"
          >
            <MenuItem value="">
              <em>None</em>
            </MenuItem>
            <MenuItem value={10}>Option 1</MenuItem>
            <MenuItem value={20}>Option 2</MenuItem>
            <MenuItem value={30}>Option 3</MenuItem>
          </Select>
        </FormControl>

        <FormControl fullWidth margin="normal">
          <Button
            variant="contained"
            component="label"
            startIcon={<CloudUploadIcon />}
          >
            Upload File
            <input
              type="file"
              hidden
              onChange={handleFileChange}
            />
          </Button>
          {selectedFile && (
            <Typography variant="body2" mt={2}>
              Selected File: {selectedFile.name}
            </Typography>
          )}
        </FormControl>

        <Button type="submit" variant="contained" color="primary" fullWidth>
          Submit
        </Button>
      </form>
    </Box>
  );
};

export default MyForm;
