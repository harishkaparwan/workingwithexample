let obj = JSON.parse(json_string);

// Function to recursively traverse and update the object
function updateDynamicParameter(obj, keyToFind, newValue) {
    Object.keys(obj).forEach(key => {
        if (key === keyToFind) {
            obj[key] = newValue;
        } else if (typeof obj[key] === 'object') {
            updateDynamicParameter(obj[key], keyToFind, newValue);
        }
    });
}

// Replace dynamic parameter
updateDynamicParameter(obj, 'city', 'Los Angeles');  // Replace 'city' value

// Serialize the object back to JSON string
json_string = JSON.stringify(obj);

console.log(json_string);
