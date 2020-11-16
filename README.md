# Latest comics service

## Brief Description
- A Restful web service, that retrieves the 20 latest comics sorted by publishing date from recent to older. 
- 10 of the comics are parsed and retrieved from PoorlyDrawnLines RSS feed.
- The other 10 comics are retrieved from remote JSON files provided by xkcd.com.

## Technology Stack
### Frameworks, Languages and libraries:
- Java 11
- Spring 5 Framework
- Spring Boot (2.4.0.RELEASE)
- lombok
- JUnit, hamcrest and SpringBootTest
    
## Normal Run and Deploy
- Change directory to project folder.
- Run the below maven command to build the project and output the executable JAR file that contains embedded web server.
    - mvn clean install
- Run the below command to run your Spring application and deploy it.
    - java -jar target/latest-comics-service.jar
     
 ## Docker Run
 To run using Docker:
 - build a local image using the below command:
    - docker build --rm -f Dockerfile -t latest-comics-service . (including this dot)
    
 - Then run using the below command:
    - docker run -p 8080:8080 latest-comics-service
  
## Available Endpoint
- /getLatestComics : returns a List of top 20 comics as JSON array.
    
