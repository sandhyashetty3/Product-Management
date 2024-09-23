# Product-Management

Server:

Terminal:

mvn clean install -DskipTests

To Start containers

cd project root directory

docker build -t  product-management .

docker run -d  --name product-management -p 8081:8080 product-management

To Stop containers

docker stop product-management

docker rm product-management


UI:

To Start containers

docker build -t product-management-ui .

docker run -d --name product-management-ui -p 3001:80 product-management-ui

To Stop containers

docker stop product-management-ui

docker rm product-management-ui

Swagger URL

http://localhost:8081/swagger-ui/index.html

UI

http://localhost:3001/

Database - H2 Embedded database

http://localhost:8081/h2-console

Jdbc url : jdbc:h2:~/shopdb

Driver classs name : org.h2.Driver

Username: sa
