#!/bin/bash

function run_frontend() {
    FRONTEND_DIR="$(pwd)/src/main/frontend/frontend"
    sudo npm install --prefix "${FRONTEND_DIR}"
    npm run ng serve --prefix "${FRONTEND_DIR}" & 
}

function run_backend() {
    sudo mvn clean package
    java -jar target/*.jar 
}

function main() { 
    run_frontend 
    run_backend 
}

main
