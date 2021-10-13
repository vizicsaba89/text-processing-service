package hu.vizicsaba.betvictortask.textprocessingservice.data.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Repository
@Log4j2
public class LoremIpsumTextProcessingRepository implements TextProcessingRepository {

    @Value(value = "${configuration.api.textApiUrl}")
    private String textApiUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> getTextResponseData(
        Integer numberOfParagraphs,
        String averageLengthOfParagraph
    ) {
        String apiUrl = MessageFormat.format(
                textApiUrl,
                numberOfParagraphs,
                averageLengthOfParagraph
        );

        log.debug("calling {}", apiUrl);

        return webClientBuilder.build()
                .get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just(""))
                .doOnError(error -> log.error(error.getMessage(), error));
    }

}
