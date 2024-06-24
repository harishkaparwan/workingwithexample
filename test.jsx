# First stage: Use Node.js base image to install Node.js dependencies
FROM node:14 as node-stage

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install Node.js dependencies
RUN npm install

# Second stage: Use Python base image to install Python dependencies
FROM python:3.9 as python-stage

# Set the working directory
WORKDIR /app

# Copy requirements.txt
COPY requirements.txt ./

# Install Python dependencies
RUN pip install -r requirements.txt

# Final stage: Use Node.js base image to combine both environments
FROM node:14

# Set the working directory
WORKDIR /app

# Copy the Node.js dependencies from the node-stage
COPY --from=node-stage /app /app

# Copy the Python dependencies from the python-stage
COPY --from=python-stage /usr/local /usr/local

# Copy the application code
COPY . .

# Expose the application port
EXPOSE 3000

# Command to run the application
CMD ["npm", "start"]
