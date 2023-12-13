const XLSX = require('xlsx');

// Example nested data
const data = [
    { id: 1, name: "Item 1", children: [
        { id: 2, name: "Item 2" },
        { id: 3, name: "Item 3", children: [
            { id: 4, name: "Item 4" }
        ]}
    ]},
    { id: 5, name: "Item 5" }
];

// Flatten the data while keeping track of hierarchy
function flattenData(data, parent = null, level = 0) {
    return data.reduce((acc, item) => {
        acc.push({ ...item, level, parentName: parent ? parent.name : "" });
        if (item.children && item.children.length) {
            acc = acc.concat(flattenData(item.children, item, level + 1));
        }
        return acc;
    }, []);
}

// Export to Excel
function exportToExcel(data, filename) {
    const flattenedData = flattenData(data);
    const processedData = flattenedData.map(item => ({
        ID: item.id,
        Name: ' '.repeat(item.level * 4) + item.name, // Indent based on level
        Parent: item.parentName
    }));

    const ws = XLSX.utils.json_to_sheet(processedData);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
    XLSX.writeFile(wb, filename);
}

exportToExcel(data, 'nestedData.xlsx');
