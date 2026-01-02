#!/bin/bash

echo "Starting deployment process..."

PROJECT_DIR="$(dirname "$0")"
JAR_FILE="$PROJECT_DIR/target/cars-0.0.1-SNAPSHOT.jar"
DEST_DIR="/srv"
TARGET_USER="openlink"

if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    echo "Please build the project first using: ./mvnw clean package"
    exit 1
fi

if [ ! -d "$DEST_DIR" ]; then
    echo "Creating directory $DEST_DIR..."
    sudo mkdir -p "$DEST_DIR"
fi

echo "Copying JAR file to $DEST_DIR..."
sudo cp "$JAR_FILE" "$DEST_DIR/"

if ! id "$TARGET_USER" &>/dev/null; then
    echo "Warning: User $TARGET_USER does not exist"
    echo "Creating user $TARGET_USER..."
    sudo useradd -r -s /bin/false "$TARGET_USER"
fi

echo "Changing ownership to $TARGET_USER..."
sudo chown "$TARGET_USER:$TARGET_USER" "$DEST_DIR/cars-0.0.1-SNAPSHOT.jar"

echo "Setting permissions..."
sudo chmod 755 "$DEST_DIR/cars-0.0.1-SNAPSHOT.jar"

echo "Deployment completed successfully!"
echo "JAR file location: $DEST_DIR/cars-0.0.1-SNAPSHOT.jar"
echo "Owner: $TARGET_USER"
echo ""
echo "To run the application:"
echo "  cd $DEST_DIR"
echo "  sudo -u $TARGET_USER java -jar cars-0.0.1-SNAPSHOT.jar"
