### Overview

Saga Orchestration with Spring Boot + Axon.


##### TODO:
* create database-per-service + store data
* compensations/rollbacks on Saga when something goes wrong
* use RabbitMQ instead of Axon Server


### Setup

- Start Axon Server: ``docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver``
- Axon Server UI: http://localhost:8024/

### Run services

* order service saga (orchestrator): ``mvn clean package spring-boot:run -pl order-service-saga-orchestrator --also-make``
* payment service: `` mvnclean package spring-boot:run -pl payment-service --also-make``
* shipping service: ``mvn clean package spring-boot:run -pl shipping-service --also-make``

### Call endpoint

curl -d '{ "currency": "USD", "itemType": "LAPTOP", "price": 8050 }' -H "Content-Type: application/json" -X POST http://localhost:8080/api/orders

* Access Axon Server UI to see events, registered services and more.