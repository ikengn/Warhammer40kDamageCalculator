#!/bin/bash
echo "---killing containers---"
docker container prune -f
echo "---creating mysql containers---"
cd mysqlserver
docker build --no-cache -t mysql_image .
echo "---creating tomcat containers---"
cd ..
docker build --no-cache -t tomcat_image .
echo "---docker compose---"
docker compose -f docker_compose.yml up -d
