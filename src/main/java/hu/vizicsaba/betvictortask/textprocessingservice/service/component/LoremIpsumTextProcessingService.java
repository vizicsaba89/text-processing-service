package hu.vizicsaba.betvictortask.textprocessingservice.service.component;

import hu.vizicsaba.betvictortask.textprocessingservice.data.repository.TextProcessingRepository;
import hu.vizicsaba.betvictortask.textprocessingservice.service.component.kafka.KafkaProducerService;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.ParagraphProcessDetails;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessRequest;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import hu.vizicsaba.betvictortask.textprocessingservice.service.util.ParagraphProcessUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Log4j2
public class LoremIpsumTextProcessingService implements TextProcessingService {

    private static final Pattern PATTERN = Pattern.compile("<p>(.+?)</p>");

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private TextProcessingRepository textProcessingRepository;

    @Override
    public Mono<TextProcessResponse> processText(TextProcessRequest textProcessRequest) {
        StopWatch responseProcessingStopWatch = new StopWatch();

        return Flux.range(textProcessRequest.getStartNumberOfParagraphs(), textProcessRequest.getEndNumberOfParagraphs())
                .doFirst(responseProcessingStopWatch::start)
                .doOnTerminate(responseProcessingStopWatch::stop)
                .flatMap(numberOfParagraphs -> textProcessingRepository.getTextResponseData(numberOfParagraphs, textProcessRequest.getAverageLengthOfParagraph()))
                .flatMap(this::getMatchedResultList)
                .map(this::replaceSpecialCharacters)
                .map(this::getParagraphProcessDetails)
                .collectList()
                .map(paragraphProcessDetailsList -> getTextProcessResponse(paragraphProcessDetailsList, responseProcessingStopWatch.getTotalTimeNanos()))
                .doOnSuccess(kafkaProducerService::send);
    }

    private Flux<String> getMatchedResultList(String textResponseData) {
        List<String> matherResultList = PATTERN.matcher(textResponseData).results()
                .map(result -> result.group(1))
                .collect(Collectors.toList());

        return Flux.fromIterable(matherResultList);
    }

    private String replaceSpecialCharacters(String result) {
        return result.replaceAll("[-+.^:,;]", "");
    }

    private ParagraphProcessDetails getParagraphProcessDetails(String matchedResult) {
        StopWatch paragraphProcessingStopWatch = new StopWatch();
        paragraphProcessingStopWatch.start();

        Map<String, Integer> wordCountMap = getWordCountMap(matchedResult);
        Integer paragraphSize = matchedResult.length();

        paragraphProcessingStopWatch.stop();

        return new ParagraphProcessDetails(wordCountMap, paragraphSize, paragraphProcessingStopWatch.getLastTaskTimeNanos());
    }

    private Map<String, Integer> getWordCountMap(String matchedResult) {
        var wordCount = new HashMap<String, Integer>();

        Arrays.stream(matchedResult.split(" "))
                .forEach(word -> wordCount.merge(word, 1, Integer::sum));

        return wordCount;
    }

    private TextProcessResponse getTextProcessResponse(List<ParagraphProcessDetails> paragraphProcessDetailsList, Long totalProcessingTime) {
        TextProcessResponse textProcessResponse = new TextProcessResponse();
        textProcessResponse.setMostFrequentWord(ParagraphProcessUtil.getMostFrequentWord(paragraphProcessDetailsList));
        textProcessResponse.setAverageParagraphSize(ParagraphProcessUtil.getAverageParagraphSize(paragraphProcessDetailsList));
        textProcessResponse.setAverageParagraphProcessingTime(ParagraphProcessUtil.getAverageParagraphProcessingTime(paragraphProcessDetailsList));
        textProcessResponse.setTotalProcessingTime(totalProcessingTime);

        return textProcessResponse;
    }

}
