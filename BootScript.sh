#!/bin/bash
sudo chmod 775 BootScript.sh
sudo sed -i -e 's/\r$//' mvnw


script_path=$(dirname "$0")

if [ "$(uname -s)" == "Linux" ] || [ -f /etc/os-release ] && grep -q "NAME=.*Linux" /etc/os-release || [[ "$OSTYPE" == "linux"* ]] ;
then
# Run the .jar file

wget "https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-x64_bin.tar.gz" -P "$script_path/src/main/resources"

cd "$script_path/src/main/resources" || exit

sudo tar -xvf openjdk-17.0.2_linux-x64_bin.tar.gz -C "$script_path/JDK"

JAVA_HOME="$script_path/src/main/resources/JDK/jdk-17.0.2"

export JAVA_HOME
# Build the project with maven

cd "$script_path/../../.."|| exit

./mvnw clean package

sudo "$JAVA_HOME/bin/java" -jar "$script_path/target/pruebaTecnicaEnsolvers.jar"

elif [ "$(uname -s)" == "Darwin" ] || [ -f /etc/os-release ] && grep -q "NAME=.*Darwin" /etc/os-release || [[ "$OSTYPE" == "darwin"* ]];
then


wget "https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_macos-x64_bin.tar.gz" -P "$script_path/src/main/resources"

cd "$script_path/src/main/resources" || exit

sudo tar -xvf openjdk-17.0.2_macos-x64_bin.tar.gz -C "$script_path/JDK"

JAVA_HOME="$script_path/src/main/resources/JDK/./jdk-17.0.2.jdk/Contents/Home"

export JAVA_HOME
# Build the project with maven

cd "$script_path/../../.."|| exit

./mvnw clean package

sudo "$JAVA_HOME/bin/java" -jar "$script_path/target/pruebaTecnicaEnsolvers.jar"

else echo "This script does not support Windows-based Systems."
fi
