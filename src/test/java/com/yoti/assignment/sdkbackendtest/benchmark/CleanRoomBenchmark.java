package com.yoti.assignment.sdkbackendtest.benchmark;

import com.yoti.assignment.sdkbackendtest.Resources;
import lombok.extern.log4j.Log4j2;
import org.openjdk.jmh.annotations.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Log4j2

@BenchmarkMode(value = {Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(
        value = 2 /* controls how many times the benchmark will be executed */
)
@Warmup(iterations = 10)
@Measurement(iterations = 5)
@Threads(value = 8 /*processors * 2*/)
public class CleanRoomBenchmark {

    private final static int PORT = 8080;

    private final static String CLEAN_ROOM_RESOURCE = "http://localhost:" + 8080 + "/api/cleaning/rooms";

    @State(value = Scope.Benchmark)
    public static class CleanRoomExecutionPlan {

        /*
            Note: each benchmark would be: iterations * filename == 5 * 4 == 20 iterations.
         */

        @Param(value = {"100", "200", "300"})
        public int iterations;

        @Param(
                value = {
                        "scenarios/first/first_scenario_input.json",
                        "scenarios/second/second_scenario_input.json",
                        "scenarios/third/third_scenario_input.json",
                        "scenarios/fourth/fourth_scenario_input.json",
                        "scenarios/fifth/fifth_scenario_input.json"
                }
        )
        public String filename;

        public RestTemplate webClient = new RestTemplate();
    }

    // -----------------------------------------------------------------------------------------------------------------


    @Benchmark
    public void testCleanRoomTime(CleanRoomExecutionPlan plan) {

        for (int i = 0; i < 100; i++) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");

            String jsonPayload = Resources.getResourceAsString(plan.filename);

            HttpEntity<String> httpEntity = new HttpEntity<>(jsonPayload, httpHeaders);

            ResponseEntity<String> responseEntity = plan.webClient.exchange(CLEAN_ROOM_RESOURCE,
                    HttpMethod.POST,
                    httpEntity,
                    String.class);
        }

    }

    /*
        Note: in order to run it, your service should be up and running.
     */
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
