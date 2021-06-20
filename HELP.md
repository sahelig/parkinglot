# Getting Started

### Assumptions for this system

1. Slot addition in a level will always happen in an ordered contiguous manner
2. Above point 1. is to ensure our checks for continuous slots
3. Future enhancements - Add slots at a position rather than using the slot number
4. Slot removal functionality isn't provided right now and is futuristic scope
5. Slots will be added in a row ordered manner - we will never add slot 1 for row 2 and
then slot 2 for row 1. This would break assumption 1.


###Running the application
1. Launch using maven - ./mvnw package && java -jar target/parkinglot-0.0.1-SNAPSHOT.jar
2. Running using docker:
    docker build -t springio/gs-spring-boot-docker .
    docker run -p 8080:8080 springio/gs-spring-boot-docker

