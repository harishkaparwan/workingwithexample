# Use an official Node.js runtime as a parent image
FROM node:14

# Set the working directory in the container
WORKDIR /app

# Install Python and pip
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

# Install the required Python packages
RUN pip3 install requests tabulate pandas numpy

# Copy your Node.js application to the container
COPY . .

# Install Node.js dependencies
RUN npm install

# Command to run your application
CMD ["node", "your-node-app.js"]
