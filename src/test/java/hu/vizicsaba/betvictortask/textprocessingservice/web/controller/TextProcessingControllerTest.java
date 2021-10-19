package hu.vizicsaba.betvictortask.textprocessingservice.web.controller;

import hu.vizicsaba.betvictortask.textprocessingservice.service.component.TextProcessingService;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessRequest;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka
public class TextProcessingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TextProcessingService textProcessingService;

    @BeforeEach
    void setup() {
        Mockito.when(textProcessingService.processText(any(TextProcessRequest.class)))
            .thenReturn(
                Mono.just(
                    new TextProcessResponse("lorem", 100.2, 45.3, 564L)
                )
        );
    }

    @Test
    @Order(1)
    @DisplayName("TextProcessingController /text endpoint  -> get text processing result without query parameters")
    public void givenEndpointForTextProcessResponse_whenEndpointCalledWithoutQueryParams_thenTextProcessResponseShouldReturned() {
        webTestClient
                .get().uri("/betvictor/text")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(response.getResponseBody() != null));
    }

    @Test
    @Order(2)
    @DisplayName("TextProcessingController /text endpoint  -> get text processing result with query parameters")
    public void givenEndpointForTextProcessResponse_whenEndpointCalledWithQueryParams_thenTextProcessResponseShouldReturned() {
        webTestClient
                .get().uri("/betvictor/text?p_start=2&p_end=7&avg_p_length=long")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(response.getResponseBody() != null));
    }

}
