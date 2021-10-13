package hu.vizicsaba.betvictortask.textprocessingservice.data.repository;

import reactor.core.publisher.Mono;

public interface TextProcessingRepository {

    Mono<String> getTextResponseData(
        Integer numberOfParagraphs,
        String averageLengthOfParagraph
    );

}
