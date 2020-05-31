mkdir executables
cd server
./mvnw clean package
mv target/server-program-group5.war ../executables
cd ../client
./mvnw clean package
mv target/client-program-group5.jar ../executables
cd ../multiplayer-server
./mvnw clean package
cd ..
