# Build the application

It is recommended to build the application with gradle tool by executing `./gradlew build` command.

# Run the application

Usage:
```
$ java -jar build/libs/bookit-1.0-SNAPSHOT.jar <in-file> <out-file>
``` 
You can run the JAR file against the test data by execution the following command:
```
$ java -jar build/libs/bookit-1.0-SNAPSHOT.jar src/test/resources/booking-requests.bookit calendar.txt
``` 