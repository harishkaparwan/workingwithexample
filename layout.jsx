'use strict';

const Hapi = require('@hapi/hapi');
const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');

const UPLOAD_DIR = path.join(__dirname, 'uploads');
const PROCESSED_DIR = path.join(__dirname, 'processed');
fs.mkdirSync(UPLOAD_DIR, { recursive: true });
fs.mkdirSync(PROCESSED_DIR, { recursive: true });

const init = async () => {

    const server = Hapi.server({
        port: 5000,
        host: 'localhost'
    });

    server.route({
        method: 'POST',
        path: '/upload',
        options: {
            payload: {
                output: 'stream',
                parse: true,
                allow: 'multipart/form-data',
                multipart: true
            }
        },
        handler: (request, h) => {
            const file = request.payload.file;
            const filePath = path.join(UPLOAD_DIR, file.hapi.filename);
            const fileStream = fs.createWriteStream(filePath);

            return new Promise((resolve, reject) => {
                file.pipe(fileStream);

                file.on('end', () => {
                    // Process the file with Python script
                    exec(`python process.py ${filePath}`, (error, stdout, stderr) => {
                        if (error) {
                            console.error(`exec error: ${error}`);
                            reject(error);
                        }

                        const processedFilePath = stdout.trim();
                        resolve(h.file(processedFilePath));
                    });
                });

                file.on('error', (err) => {
                    reject(err);
                });
            });
        }
    });

    await server.start();
    console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});

init();
