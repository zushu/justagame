# Back-end Implementation Notes

## General Structure
Our back-end implementation has basically two parts: RESTful web services and local game logic. For RESTful services a postman collection is already provided. The other part contains classes for our game. In that part we used Inheritence and Decorator design pattern. By decorator pattern we had different type of Aliens, which is still open for diversification.

## About Postman Collection
There is a postman collection provided **"ceng453project.postman_collection.json"**. This collection includes example calls for all of our RESTful web services. In the collection there are two folders namely User and Score. These folders includes operations related with tables with same names. Request names and descriptions are also provided clearly in the collection.

## Database
We have two simple tables for now. These are User and Score tables. User table will be used for authorization purpose, and score table will keep past game scores of users. ER Diagram of this simple database is named **"ERDiagram.png"** in the project's base folder.
 


