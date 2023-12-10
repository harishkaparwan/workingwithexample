import React, { useState } from 'react';

const DynamicForm = ({ formFields }) => {
    const [formData, setFormData] = useState({});

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
        // Handle form submission
    };

    return (
        <form onSubmit={handleSubmit}>
            {formFields.map((field, index) => {
                switch (field.type) {
                    case 'text':
                    case 'date':
                    case 'email':
                    case 'password':
                    case 'number':
                        return (
                            <input
                                key={index}
                                type={field.type}
                                name={field.name}
                                placeholder={field.placeholder}
                                style={field.style}
                                onChange={handleChange}
                            />
                        );
                    case 'select':
                        return (
                            <select
                                key={index}
                                name={field.name}
                                style={field.style}
                                onChange={handleChange}
                            >
                                {field.options.map((option, optIndex) => (
                                    <option key={optIndex} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </select>
                        );
                    // Handle other field types...
                    default:
                        return null;
                }
            })}
            <button type="submit" style={{ padding: "10px", marginTop: "10px" }}>
                Submit
            </button>
        </form>
    );
};

export default DynamicForm;




const api = axios.create({
    baseURL: 'https://your-api-url.com/',
    // You can add more default settings if needed
});
// In your App component or wherever you use the DynamicForm component
import React from 'react';
import DynamicForm from './DynamicForm';
import formJson from './formConfig.json';
import api from './api'; // Import the Axios instance

const App = () => {
    return (
        <div>
            <DynamicForm formFields={formJson} api={api} />
        </div>
    );
};

export default App;




import React, { useState } from 'react';

const DynamicForm = ({ formFields, api }) => {
    const [formData, setFormData] = useState({});

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/submit-form', formData);
            console.log(response.data);
            // Handle the response
        } catch (error) {
            console.error('Error submitting form:', error);
            // Handle errors
        }
    };

    // Render form fields...
};

export default DynamicForm;
