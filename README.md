# CENG453 Term Project

## Introduction
This repository includes a desktop game Alien Shooter. The player shoots aliens, avoids being shot by aliens and colliding with them. The game has 5 levels with increasing difficulties. The first 4 levels are single-player. The final level is multiplayer where two players try to kill one alien.

## Dependencies
Java version 13 was used in implementation.

## Building the project
Go to `group5` directory and run the following commands:
```
sh build.sh
```

## Back-end Implementation Notes

### General Structure
Our back-end implementation has basically two parts: RESTful web services and local game logic. For RESTful services a postman collection is already provided. The other part contains classes for our game. In that part we used Inheritence and Decorator design pattern. By decorator pattern we had different type of Aliens, which is still open for diversification.

### About Postman Collection
There is a postman collection provided **"ceng453project.postman_collection.json"**. This collection includes example calls for all of our RESTful web services. In the collection there are two folders namely User and Score. These folders includes operations related with tables with same names. Request names and descriptions are also provided clearly in the collection.

### Database
We have two simple tables for now. These are User and Score tables. User table will be used for authorization purpose, and score table will keep past game scores of users. ER Diagram of this simple database is named **"ERDiagram.png"** in the project's base folder.

### Run Commands

* To run the **server side** of the game, execute `java - jar server-program-group5.war` command under `group5/executables/` folder.
* To run **unit tests** execute `./mvnw -Dtest=GameObjectTest test` command under `group5/server/` folder.

## Front-end Implementation Notes

Client side was built using JavaFX. 
Users can sign up, sign in and play the game using the interface. After a game is finished, the score of the user is added to the database and shown in the leaderboard. 

### Run Commands
* To run the **client side** of the game, execute `java -jar client-program-group5.jar` command under `group5/executables/` folder.
* In order to sign up and log in to start the game, back-end should be run in parallel.

## Multiplayer Level Implementation Notes

In the multiplayer level, two players attack the same alien. When a player finishes the first four levels, they wait for a matching player who also finished the first four levels to join them and the final level starts. In order to play the multiplayer level, the user should run the following commands alongside with server and client programs **before starting the game**.

### Run Commands

* To run the **multiplayer server**, execute `java -jar multiplayer-program-group5.jar` command under `group5/executables/` folder. 
