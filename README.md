# Spring Boot Rest Service with R2DBC and MySql 8.2.0
The service is running standalone and in Docker, depending on which profile is used. The database is installed in Docker and running.

Further information on the topic:

- https://www.bezkoder.com/spring-r2dbc-mysql/
- https://www.baeldung.com/r2dbc

## Build, install and run CountryDbAccessService in Docker Container
Prerequisite: The DB is already running in Docker (due to IP address assignment)

- mvn clean install -Plocal-docker-desktop
- docker buildx build --platform linux/arm64 -t country-db-access-service .
- docker run --name CountryDbAccessService -p 8081:8080 -t country-db-access-service

Further information about build containers:

- https://severalnines.com/blog/mysql-docker-containers-understanding-basics
- https://www.docker.com/blog/9-tips-for-containerizing-your-spring-boot-code/
- https://hub.docker.com/_/openjdk

## Execute CountryDbAccessService locally
In VISUAL STUDIO CODE with Spring Boot Dashboard:

- Go to Spring Boot Dashboard and select 'Run with Profile...' under APPS and in the context menu. Then select local in the window that appears

## Install, operate and maintain database in Docker
- Install MySQL database as a container:
  docker run --name MySql8.2.0 -p 3306:3306 -p 33060:33060 -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=mydb -e MYSQL_ROOT_HOST=172.17.0.1 -e MYSQL_USER=dbadmin -e MYSQL_PASSWORD=dbadmin -dit mysql:latest
- Maintain database in the container:
  docker exec -it mysql mysql -uroot -padmin
- Connect and maintain database with DB client from HOST
  - Connection parameters
    - Connection method: TCP/IP
    - Url: 127.0.0.1
    - Port: 3306
    - User: root
    - Password: admin

## Queries when everything is running
- GET: http://localhost:8081/country/countries/delay
- GET: http://localhost:8081/country/countries
- GET: http://localhost:8081/country/name/USA
- GET: http://localhost:8081/country/continent/Europa
- DELETE: http://localhost:8081/country/delete/USA
- POST:

### Docker commands to maintain container
- docker network ls
- docker network inspect bridge
- docker ps
  - Displays the following information for all containers: CONTAINER ID, IMAGE, COMMAND, CREATED, STATUS, PORTS, NAMES
- docker inspect <CONTAINER ID> | grep "IPAddress"
  - Returns the Docker internal IP address
- docker network inspect <network> default is 'bridge' -> docker network inspect bridge

Further information on the topic:

- https://maximorlov.com/4-reasons-why-your-docker-containers-cant-talk-to-each-other/

## VS Code
- in planning! run local: "vmArgs": " -Dspring.profiles.active=local"

## Docker useful commands
- docker network create --driver bridge project
- docker network inspect project
- docker run --name MySql8.2.0 -p 3306:3306 -p 33060:33060 -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=mydb -e MYSQL_ROOT_HOST=172.18.0.1 -e MYSQL_USER=dbadmin -e MYSQL_PASSWORD=dbadmin -dit --network project mysql:latest
