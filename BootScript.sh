#!/bin/bash

OS="$(uname -s)"
ARCH="$(uname -m)"

# To find a specific version and more visit

# NODE js, https://nodejs.org/en/download
NODE_VERSION="v20.9.0"

#Maven, The Apache Maven Project, https://maven.apache.org/download.cgi
MAVEN_VERSION="3.9.5"

# JAVA by Azul (support ARM), https://www.azul.com/downloads/#zulu
ZULU_VERSION="17.46.19"
JDK_VERSION="17.0.9"

function move_and_delete() {
    sudo tar -xvf "$1" -C /opt;
    rm -rf "$1"
}

function setup_os {
    if [ "${OS}" == "Linux" ] && [ "${ARCH}" == "x86_64" ]; then
        # jdk/maven environments
        OS="linux"
        ARCH="x64"
        # node environmets
        OS_NODE="linux"
        FORMAT_COMPRESS_NODE="xz"
    elif [ "${OS}" == "Darwin" ]; then
        # jdk/maven environments
        OS="macosx"
        # node environmets
        OS_NODE="darwin"
        FORMAT_COMPRESS_NODE="gz"
    else
        echo "Not compatible"
        exit 1
    fi
}

function setup_environment() {
    BASE_URL="$1"
    FILENAME="$2"

    curl --fail -O "${BASE_URL}/${FILENAME}"
    move_and_delete "${FILENAME}" 
}


function setup_jdk() {
    setup_environment "https://cdn.azul.com/zulu/bin/" "zulu${ZULU_VERSION}-ca-jdk${JDK_VERSION}-${OS}_${ARCH}.tar.gz"

    JAVA_HOME="/opt/zulu${ZULU_VERSION}-ca-jdk${JDK_VERSION}-${OS}_${ARCH}"
}

function setup_maven() {
    setup_environment "https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries" "apache-maven-${MAVEN_VERSION}-bin.tar.gz"

    M2_HOME="/opt/apache-maven-${MAVEN_VERSION}"
}


function setup_node() {
    setup_environment "https://nodejs.org/dist/${NODE_VERSION}" "node-${NODE_VERSION}-${OS_NODE}-${ARCH}.tar.${FORMAT_COMPRESS_NODE}"

    NODE_HOME="/opt/node-${NODE_VERSION}-${OS_NODE}-${ARCH}"
}

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
    setup_os
    setup_node
    setup_maven
    setup_jdk

    export PATH="${JAVA_HOME}/bin:${M2_HOME}/bin:${NODE_HOME}/bin:${PATH}"

    run_frontend 
    run_backend 
}

main
