function getPythonVersion() {
  exec('python3 --version', (error, stdout, stderr) => {
    if (error) {
      console.error(`Error executing Python command: ${error.message}`);
      return;
    }
    if (stderr) {
      console.error(`Python stderr: ${stderr}`);
      return;
    }
    console.log(`Python version: ${stdout}`);
  });

  exec('python --version', (error, stdout, stderr) => {
    if (error) {
      console.error(`Error executing Python command: ${error.message}`);
      return;
    }
    if (stderr) {
      console.error(`Python stderr: ${stderr}`);
      return;
    }
    console.log(`Python version: ${stdout}`);
  });
}

// Call the function
getPythonVersion();
