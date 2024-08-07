To achieve this, you'll need to follow these steps:

1. **Set up your Node.js environment**: Install the necessary packages.
2. **Connect to MongoDB**: Retrieve the Postman collections and environments stored in the database.
3. **Run the collections with Newman**.

### Step-by-Step Guide

1. **Set up your Node.js environment**

First, ensure you have Node.js installed. Initialize a new Node.js project and install the required dependencies:

```bash
mkdir postman-runner
cd postman-runner
npm init -y
npm install newman mongodb
```

2. **Set up MongoDB**

Make sure you have a MongoDB database set up with collections for your Postman collections and environments. Each document in these collections should store the respective Postman JSON data.

Example structure for MongoDB collections:
- **postman_collections**:
  ```json
  {
    "_id": "collection1",
    "name": "Sample Collection",
    "data": { ... } // Postman collection JSON data
  }
  ```

- **postman_environments**:
  ```json
  {
    "_id": "environment1",
    "name": "Sample Environment",
    "data": { ... } // Postman environment JSON data
  }
  ```

3. **Create the script to retrieve and run collections**

Create a script named `runCollections.js`:

```javascript
const newman = require('newman');
const { MongoClient } = require('mongodb');

const mongoUri = 'mongodb://localhost:27017'; // Replace with your MongoDB URI
const dbName = 'your_database_name'; // Replace with your database name

const runPostmanCollection = async (collectionId, environmentId) => {
    const client = new MongoClient(mongoUri, { useNewUrlParser: true, useUnifiedTopology: true });

    try {
        await client.connect();
        const db = client.db(dbName);

        const collection = await db.collection('postman_collections').findOne({ _id: collectionId });
        if (!collection) {
            throw new Error(`Collection with ID ${collectionId} not found`);
        }

        let environment = null;
        if (environmentId) {
            environment = await db.collection('postman_environments').findOne({ _id: environmentId });
            if (!environment) {
                throw new Error(`Environment with ID ${environmentId} not found`);
            }
        }

        newman.run({
            collection: collection.data,
            environment: environment ? environment.data : undefined,
            reporters: 'cli',
        }, (err) => {
            if (err) {
                throw err;
            }
            console.log('Collection run complete!');
        });
    } catch (err) {
        console.error('Error running collection:', err);
    } finally {
        await client.close();
    }
};

// Example usage: runPostmanCollection('collection1', 'environment1');
// You can change 'collection1' and 'environment1' to the IDs of your collection and environment in MongoDB

runPostmanCollection('collection1', 'environment1');
```

### Explanation

1. **Import dependencies**: Import the `newman` and `mongodb` packages.

2. **MongoDB Connection**: Establish a connection to your MongoDB database using the `MongoClient` from the `mongodb` package.

3. **Retrieve Collections and Environments**: Query the MongoDB collections to retrieve the Postman collection and environment by their IDs.

4. **Run Collections with Newman**: Use `newman.run()` to execute the retrieved Postman collection and environment.

5. **Error Handling**: Handle errors gracefully by logging them and ensuring the database connection is closed properly.

6. **Example Usage**: Modify the `runPostmanCollection` function call with the IDs of your collection and environment stored in MongoDB.

### Running the Script

To run the script, use the following command:

```bash
node runCollections.js
```

This script will connect to MongoDB, retrieve the specified Postman collection and environment, and execute them using Newman, displaying the results in the terminal.
