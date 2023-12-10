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
