server.route({
        method: 'GET',
        path: '/scripts',
        handler: (request, h) => {
            const scriptsDir = path.join(__dirname, '..', 'scripts');
            return new Promise((resolve, reject) => {
                fs.readdir(scriptsDir, (err, files) => {
                    if (err) {
                        return reject(err);
                    }
                    resolve(files);
                });
            });
        }
    });
