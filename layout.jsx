 server.route({
        method: 'GET',
        path: '/run-python',
        handler: (request, h) => {
            return new Promise((resolve, reject) => {
                exec('python3 script.py', (error, stdout, stderr) => {
                    if (error) {
                        console.error(`exec error: ${error}`);
                        return reject(error);
                    }
                    if (stderr) {
                        console.error(`stderr: ${stderr}`);
                        return reject(stderr);
                    }
                    resolve(stdout);
                });
            });
        }
    });





server.route({
        method: 'POST',
        path: '/run-python',
        handler: (request, h) => {
            const { script } = request.payload;

            return new Promise((resolve, reject) => {
                exec(`python3 ${script}`, (error, stdout, stderr) => {
                    if (error) {
                        console.error(`exec error: ${error}`);
                        return reject(error);
                    }
                    if (stderr) {
                        console.error(`stderr: ${stderr}`);
                        return reject(stderr);
                    }
                    resolve(stdout);
                });
            });
        },
        options: {
            payload: {
                parse: true,
                multipart: true
            }
        }
    });
