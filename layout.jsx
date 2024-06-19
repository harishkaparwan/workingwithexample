import React, { useState, useEffect } from 'react';

const ScriptDropdown = () => {
    const [scripts, setScripts] = useState([]);
    const [selectedScript, setSelectedScript] = useState('');

    useEffect(() => {
        fetch('/scripts')
            .then(response => response.json())
            .then(data => setScripts(data))
            .catch(error => console.error('Error fetching scripts:', error));
    }, []);

    const handleChange = (event) => {
        setSelectedScript(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Selected script:', selectedScript);
        // Here you can add logic to send the selected script to your server for execution
    };

    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="script">Choose a script:</label>
            <select id="script" name="script" value={selectedScript} onChange={handleChange}>
                <option value="">Select a script</option>
                {scripts.map((script, index) => (
                    <option key={index} value={script}>
                        {script}
                    </option>
                ))}
            </select>
            <button type="submit">Run</button>
        </form>
    );
};

export default ScriptDropdown;
