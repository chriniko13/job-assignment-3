package com.yoti.assignment.sdkbackendtest.it;


import com.yoti.assignment.sdkbackendtest.Resources;
import com.yoti.assignment.sdkbackendtest.SdkBackendTestApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SdkBackendTestApplication.class,
        properties = {"application.properties"}
)


@RunWith(SpringRunner.class)

public class SpecificationIT {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @Before
    public void before() {
        webClient = WebClient.create("http://localhost:" + port + "/api/cleaning");
    }

    @Test
    public void hello_endpoint_works_as_expected() {
        // when
        ClientResponse response = webClient
                .get()
                .uri("/hello")
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);


        // then
        String body = response.bodyToMono(String.class).block();
        Assert.assertEquals("hello", body);
    }

    @Test
    public void first_scenario() throws Exception {

        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/first/first_scenario_input.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.OK, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(Resources.getResourceAsString("scenarios/first/first_scenario_output.json"), bodyAsString, true);
    }

    @Test
    public void second_scenario() throws Exception {

        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/second/second_scenario_input.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.OK, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(Resources.getResourceAsString("scenarios/second/second_scenario_output.json"), bodyAsString, true);
    }

    @Test
    public void third_scenario() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/third/third_scenario_input.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.OK, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(Resources.getResourceAsString("scenarios/third/third_scenario_output.json"), bodyAsString, true);
    }

    @Test
    public void fourth_scenario() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/fourth/fourth_scenario_input.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.OK, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(Resources.getResourceAsString("scenarios/fourth/fourth_scenario_output.json"), bodyAsString, true);
    }

    @Test
    public void fifth_scenario() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/fifth/fifth_scenario_input.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.OK, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(Resources.getResourceAsString("scenarios/fifth/fifth_scenario_output.json"), bodyAsString, true);
    }


}
