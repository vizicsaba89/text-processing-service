package hu.vizicsaba.betvictortask.textprocessingservice.web.controller;

import hu.vizicsaba.betvictortask.textprocessingservice.service.component.TextProcessingService;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessRequest;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("betvictor")
@CrossOrigin
public class TextProcessingController {

    @Autowired
    private TextProcessingService textProcessingService;

    @GetMapping(path = "/text", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Mono<TextProcessResponse> getTextProcessResponse(
        @RequestParam(value = "p_start", defaultValue = "1") Integer startNumberOfParagraphs,
        @RequestParam(value = "p_end", defaultValue = "1") Integer endNumberOfParagraphs,
        @RequestParam(value = "avg_p_length", defaultValue = "long") String averageLengthOfParagraph
    ) {
        return textProcessingService.processText(
            new TextProcessRequest(startNumberOfParagraphs, endNumberOfParagraphs, averageLengthOfParagraph)
        );
    }

}
