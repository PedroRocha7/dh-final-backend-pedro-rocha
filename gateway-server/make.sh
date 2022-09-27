mvn clean && mvn package -DskipTests
docker build . -t gateway-server