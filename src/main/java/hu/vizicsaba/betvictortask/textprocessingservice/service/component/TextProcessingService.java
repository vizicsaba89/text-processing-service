package hu.vizicsaba.betvictortask.textprocessingservice.service.component;

import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessRequest;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import reactor.core.publisher.Mono;

public interface TextProcessingService {

    Mono<TextProcessResponse> processText(TextProcessRequest textProcessRequest);

}
