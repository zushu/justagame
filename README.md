# CENG453 Term Project

## Introduction
This repository includes a single-player desktop game Alien Shooter. The player shoots aliens, avoids being shot by aliens and colliding with them. Currently, the game has 4 levels with increasing difficulties. 

## Dependencies
Java version 13 was used in implementation.

## Back-end Implementation Notes

### General Structure
Our back-end implementation has basically two parts: RESTful web services and local game logic. For RESTful services a postman collection is already provided. The other part contains classes for our game. In that part we used Inheritence and Decorator design pattern. By decorator pattern we had different type of Aliens, which is still open for diversification.

### About Postman Collection
There is a postman collection provided **"ceng453project.postman_collection.json"**. This collection includes example calls for all of our RESTful web services. In the collection there are two folders namely User and Score. These folders includes operations related with tables with same names. Request names and descriptions are also provided clearly in the collection.

### Database
We have two simple tables for now. These are User and Score tables. User table will be used for authorization purpose, and score table will keep past game scores of users. ER Diagram of this simple database is named **"ERDiagram.png"** in the project's base folder.

### Run Commands

* To run **spring-boot application** execute **./mvnw spring-boot:run** command under group5/server/ folder.
* To run **unit tests** execute **./mvnw -Dtest=GameObjectTest test** command under group5/server/ folder.

## Front-end Implementation Notes

Client side was built using JavaFX. 
Users can sign up, sign in and play the game using the interface. After a game is finished, the score of the user is added to the database and shown in the leaderboard. 

### Run Commands
To run the frontend application: execute **./mvnw clean javafx:run** command under group5/client/ folder.

