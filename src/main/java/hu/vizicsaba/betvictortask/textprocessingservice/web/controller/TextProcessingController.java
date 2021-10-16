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
        @RequestParam("p_start") Integer startNumberOfParagraphs,
        @RequestParam("p_end") Integer endNumberOfParagraphs,
        @RequestParam("avg_p_length") String averageLengthOfParagraph
    ) {
        return textProcessingService.processText(
            new TextProcessRequest(startNumberOfParagraphs, endNumberOfParagraphs, averageLengthOfParagraph)
        );
    }

}
