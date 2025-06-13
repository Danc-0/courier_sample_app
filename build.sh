#!/bin/bash

# build.sh - Complete build script for TeamCity

set -e  # Exit on any error

echo "=== Starting SpringBoot Application Build ==="

# Build variables
APP_NAME="my-spring-app"
VERSION=${BUILD_NUMBER:-"1.0.0"}
IMAGE_TAG="${APP_NAME}:${VERSION}"

echo "Building version: ${VERSION}"

# Step 1: Run tests
echo "=== Running Tests ==="
./mvnw clean test

# Step 2: Build JAR
echo "=== Building JAR ==="
./mvnw clean package -DskipTests

# Step 3: Build Docker image
echo "=== Building Docker Image ==="
docker build -t ${IMAGE_TAG} .
docker tag ${IMAGE_TAG} ${APP_NAME}:latest

# Step 4: Run integration tests on Docker image
echo "=== Running Integration Tests ==="
docker run -d --name test-container -p 8081:8080 ${IMAGE_TAG}

# Wait for application to start
sleep 30

# Health check
curl -f http://localhost:8081/actuator/health || {
    echo "Health check failed"
    docker logs test-container
    docker stop test-container
    docker rm test-container
    exit 1
}

echo "Health check passed"

# Cleanup test container
docker stop test-container
docker rm test-container

# Step 5: Push to registry (if configured)
if [ -n "${DOCKER_REGISTRY}" ]; then
    echo "=== Pushing to Registry ==="
    docker tag ${IMAGE_TAG} ${DOCKER_REGISTRY}/${IMAGE_TAG}
    docker push ${DOCKER_REGISTRY}/${IMAGE_TAG}
fi

echo "=== Build Completed Successfully ==="
echo "Image: ${IMAGE_TAG}"