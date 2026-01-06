#!/bin/bash

# Script to deploy the Car Details Application to Linux /srv directory
# This script changes ownership to 'openlink' user and moves the JAR to /srv

JAR_FILE="CarDetailsApp-1.0.0.jar"
TARGET_USER="openlink"
TARGET_DIR="/srv"

# Check if JAR file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: $JAR_FILE not found in current directory"
    exit 1
fi

# Check if target user exists
if ! id "$TARGET_USER" &>/dev/null; then
    echo "Error: User '$TARGET_USER' does not exist"
    exit 1
fi

# Change ownership of the JAR file to the target user
echo "Changing ownership of $JAR_FILE to $TARGET_USER..."
sudo chown $TARGET_USER:$TARGET_USER "$JAR_FILE"

# Check if ownership change was successful
if [ $? -eq 0 ]; then
    echo "Ownership changed successfully"
else
    echo "Error: Failed to change ownership"
    exit 1
fi

# Create /srv directory if it doesn't exist
if [ ! -d "$TARGET_DIR" ]; then
    echo "Creating $TARGET_DIR directory..."
    sudo mkdir -p "$TARGET_DIR"
fi

# Move the JAR file to /srv directory
echo "Moving $JAR_FILE to $TARGET_DIR..."
sudo mv "$JAR_FILE" "$TARGET_DIR/"

# Check if move was successful
if [ $? -eq 0 ]; then
    echo "File moved successfully to $TARGET_DIR"
    echo "Deployment complete!"
    echo "JAR location: $TARGET_DIR/$JAR_FILE"
    echo "Owner: $TARGET_USER"
    
    # Display file info
    echo ""
    echo "File details:"
    ls -lah "$TARGET_DIR/$JAR_FILE"
else
    echo "Error: Failed to move $JAR_FILE"
    exit 1
fi
