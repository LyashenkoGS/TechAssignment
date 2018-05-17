## REST service tech assignment

Please complete the assignment considering the quality and readability.
 
Using Java Spring implement a server application that can be executed from command line:
- The server application is listening on the random TCP port you use and it has two endpoints available: /assignment and /ingest
- When the user visits /assignment, the server must return a JSON object with the content of https://jsonplaceholder.typicode.com/posts but with the title and body content on the reverse order in which is given at https://jsonplaceholder.typicode.com/posts
- When the user submits any text plain to /ingest, the server returns a URL in which the user can download an image containing the text submitted
- Deliver a URL and git repository implementing the mentioned server application. Please deliver this via email at least one day before your next interview.


## Prerequisites

* Java 1.8
* maven

## How to build

Executing test requires access to the Internet 

    mvn clean install
    
## How to use

    java -jar target/tech-assignment-*.jar 

Get assignments list

    curl -X GET http://127.0.0.1:8080/assignment 
    
Generate an image from text

    curl -X POST http://127.0.0.1:8080/ingest  -d someTextToGenerateImageFrom

    
 Download the generated image
 
  [http://127.0.0.1:8080/download_image/D41D8CD98F00B204E9800998ECF8427E.png](http://127.0.0.1:8080/download_image/D41D8CD98F00B204E9800998ECF8427E.png)