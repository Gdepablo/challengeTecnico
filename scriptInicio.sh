#!/bin/bash
chmod +x script1.sh
nohup java -jar /path/to/app/hello-world.jar > /path/to/log.txt 2>&1 &
echo $! > /path/to/app/pid.file