package com.yoti.assignment.sdkbackendtest.it;


import com.yoti.assignment.sdkbackendtest.Resources;
import com.yoti.assignment.sdkbackendtest.SdkBackendTestApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
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
public class ValidationSpecificationIT {


    @LocalServerPort
    private int port;

    private WebClient webClient;

    @Before
    public void before() {
        webClient = WebClient.create("http://localhost:" + port + "/api/cleaning");
    }

    @Test
    public void validate_room_size_works_as_expected_x_first_case() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/x_room_size_case_one.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/x_room_size_case_one_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );
    }

    @Test
    public void validate_room_size_works_as_expected_x_second_case() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/x_room_size_case_two.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/x_room_size_case_one_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );
    }

    @Test
    public void validate_room_size_works_as_expected_y_first_case() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/y_room_size_case_one.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/y_room_size_case_one_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );
    }

    @Test
    public void validate_room_size_works_as_expected_y_second_case() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/y_room_size_case_two.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/y_room_size_case_one_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );
    }

    @Test
    public void validate_hoover_coords_works_as_expected() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/hoover_coords_case.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/hoover_coords_case_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );
    }

    @Test
    public void validate_dirt_patches_works_as_expected() throws Exception {
        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/dirt_patches_case.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/dirt_patches_case_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );
    }

    @Test
    public void validate_instructions_works_as_expected_empty_case() throws Exception {

        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/instructions_case_one.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/instructions_case_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );


    }

    @Test
    public void validate_instructions_works_as_expected_null_case() throws Exception {

        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/instructions_case_three.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/instructions_case_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );


    }

    @Test
    public void validate_instructions_works_as_expected_not_empty_case() throws Exception {

        // when
        ClientResponse clientResponse = webClient
                .post()
                .uri("/rooms")
                .body(BodyInserters.fromResource(new ClassPathResource("scenarios/validation/instructions_case_two.json")))
                .exchange()
                .blockOptional()
                .orElseThrow(IllegalStateException::new);

        // then
        HttpStatus httpStatus = clientResponse.statusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        String bodyAsString = clientResponse.bodyToMono(String.class).block();

        JSONAssert.assertEquals(
                Resources.getResourceAsString("scenarios/validation/instructions_case_response.json"),
                bodyAsString,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );


    }
}
