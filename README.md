### Yoti Backend Assignment Test

##### Travis Status:
[![Build Status](https://travis-ci.com/chriniko13/job-assignment-3.svg?branch=master)](https://travis-ci.com/chriniko13/job-assignment-3)

##### Assignee: Nikolaos Christidis (nick.christidis@yahoo.com)


#### Prequisities in order to run
1) Docker Compose


#### How to run service
* Execute: `docker-compose up`

* Two options:
    * Execute: 
        * `mvn clean package`
        * `java -jar target/sdk-backend-test-1.0.0-SNAPSHOT.jar -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`
                
    * Execute:
        * `mvn spring-boot:run -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`

* (Optional) When you finish: `docker-compose down`

#### Execute Unit Tests
* Execute: `mvn clean test`

#### Execute Integration Tests (you should run docker-compose up first)
* Execute: `mvn clean integration-test` or `mvn clean verify`

#### Test Coverage (via JaCoCo)
* In order to generate reports execute: `mvn clean verify`
    * In order to see unit test coverage open with browser: `target/site/jacoco-ut/index.html`
    * In order to see integration test coverage open with browser: `target/site/jacoco-it/index.html`

#### Code Quality via SonarQube (you should run docker-compose up first)
* First login to web portal: http://localhost:9000/sessions/new
    * Credentials
        * Username: admin
        * Password: admin
        
* Then execute: `mvn sonar:sonar` and browse to: `http://localhost:9000/projects` and select: `sdk-backend-test` in
order to see more information for code.

#### Execute Performance/Benchmark Tests (JMH)
* In order to run performance/benchmark tests you need to have our service up and running

* Then go to: `src/test/java/../benchmark/CleanRoomBenchmark.java` and run `main` method from your IDE.
Wait a little and then you should have a throughput result like the following.

* Throughput result from a run (with: `@Threads(value = 8 /*processors * 2*/)`):
    
    ```
    Result "com.yoti.assignment.sdkbackendtest.benchmark.CleanRoomBenchmark.testCleanRoomTime":
      1575.366 ±(99.9%) 279.486 ms/op [Average]
      (min, avg, max) = (1332.064, 1575.366, 2007.878), stdev = 184.863
      CI (99.9%): [1295.880, 1854.852] (assumes normal distribution)
    
    
    # Run complete. Total time: 00:20:11
    
    Benchmark                                                              (filename)  (iterations)  Mode  Cnt     Score     Error  Units
    CleanRoomBenchmark.testCleanRoomTime    scenarios/first/first_scenario_input.json           100  avgt   10   550.306 ±  64.633  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/first/first_scenario_input.json           200  avgt   10   492.329 ±  77.620  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/first/first_scenario_input.json           300  avgt   10   482.343 ±  23.943  ms/op
    CleanRoomBenchmark.testCleanRoomTime  scenarios/second/second_scenario_input.json           100  avgt   10   516.485 ±  40.232  ms/op
    CleanRoomBenchmark.testCleanRoomTime  scenarios/second/second_scenario_input.json           200  avgt   10   565.647 ±  57.943  ms/op
    CleanRoomBenchmark.testCleanRoomTime  scenarios/second/second_scenario_input.json           300  avgt   10   541.728 ±  41.657  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/third/third_scenario_input.json           100  avgt   10   592.921 ±  57.019  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/third/third_scenario_input.json           200  avgt   10   574.793 ± 109.783  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/third/third_scenario_input.json           300  avgt   10   499.821 ±  30.255  ms/op
    CleanRoomBenchmark.testCleanRoomTime  scenarios/fourth/fourth_scenario_input.json           100  avgt   10   508.025 ±  24.438  ms/op
    CleanRoomBenchmark.testCleanRoomTime  scenarios/fourth/fourth_scenario_input.json           200  avgt   10   517.689 ±  37.611  ms/op
    CleanRoomBenchmark.testCleanRoomTime  scenarios/fourth/fourth_scenario_input.json           300  avgt   10   574.466 ±  54.975  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/fifth/fifth_scenario_input.json           100  avgt   10  1769.672 ± 458.129  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/fifth/fifth_scenario_input.json           200  avgt   10  1616.064 ± 364.854  ms/op
    CleanRoomBenchmark.testCleanRoomTime    scenarios/fifth/fifth_scenario_input.json           300  avgt   10  1575.366 ± 279.486  ms/op
    
    Process finished with exit code 0

    ```
    
* VisualVM screenshots: 
    * [First Icon](benchmark1.png)
    * [Second Icon](benchmark2.png)
    * [Third Icon](benchmark3.png)
    * [Fourth Icon](benchmark4.png)

#### Additional Features

* Print current state of room in every repetition:
    * In order to enable/disable it, you have to play with configuration: `print.cleaning.state`
      in `application-dev.properties` and `application-int.properties`.
      
    * Example:
        ```
        Pending direction commands: NORTH,NORTH,EAST,SOUTH,EAST,EAST,SOUTH,WEST,NORTH,WEST,WEST
        Next which will be executed: NORTH
        
        .  .  .  .  .  
        .  .  !  .  .  
        .  H  !  .  .  
        .  .  .  .  .  
        .  !  .  .  .  
        
        2019-02-25 11:19:17.971 DEBUG chriniko-desktop --- [      elastic-2] c.y.a.s.d.Hoover                         : 
        Pending direction commands: NORTH,EAST,SOUTH,EAST,EAST,SOUTH,WEST,NORTH,WEST,WEST
        Next which will be executed: NORTH
        
        .  .  .  .  .  
        .  H  !  .  .  
        .  .  !  .  .  
        .  .  .  .  .  
        .  !  .  .  .  
        
        
         ........
        
        ```

#### Persistence

* Sample Record:
    ```json
    {
        "_id" : ObjectId("5c717b6faf3d6a6144492b56"),
        "input" : {
            "roomSize" : [ 
                5, 
                5
            ],
            "coords" : [ 
                1, 
                2
            ],
            "patches" : [ 
                [ 
                    1, 
                    0
                ], 
                [ 
                    2, 
                    2
                ], 
                [ 
                    2, 
                    3
                ]
            ],
            "instructions" : "NNESEESWWSSWWE"
        },
        "output" : {
            "coords" : [ 
                1, 
                0
            ],
            "patches" : 3
        },
        "createdBy" : "24900@chriniko-desktop",
        "createdDate" : ISODate("2019-02-23T16:57:19.636Z"),
        "version" : NumberLong(0),
        "delete" : false,
        "_class" : "com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationResult"
    }
    ```

#### Useful Docker Commands

* In order to connect to sonarqube postgresql

    * Execute: `docker ps` and find the id of your running container
    * Then in order to find the host of postgre running container execute: `docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_id>`
