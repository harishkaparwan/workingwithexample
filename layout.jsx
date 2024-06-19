import React, { useState } from 'react';
import axios from 'axios';

const App = () => {
  const [selectedScript, setSelectedScript] = useState('');
  const [output, setOutput] = useState('');
  const [error, setError] = useState('');

  const handleSelectChange = (e) => {
    setSelectedScript(e.target.value);
  };

  const executeScript = async () => {
    try {
      const response = await axios.post('http://localhost:5000/run-script', { scriptName: selectedScript });
      setOutput(response.data.output);
      setError(response.data.error);
    } catch (err) {
      setError('Error executing script');
    }
  };

  return (
    <div>
      <h1>Run Python Script</h1>
      <select value={selectedScript} onChange={handleSelectChange}>
        <option value="">Select a script</option>
        <option value="script1">Script 1</option>
        <option value="script2">Script 2</option>
        {/* Add more options as needed */}
      </select>
      <button onClick={executeScript}>Run</button>
      <div>
        <h2>Output</h2>
        <pre>{output}</pre>
        <h2>Error</h2>
        <pre>{error}</pre>
      </div>
    </div>
  );
};

export default App;
