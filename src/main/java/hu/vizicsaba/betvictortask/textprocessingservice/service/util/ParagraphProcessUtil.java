package hu.vizicsaba.betvictortask.textprocessingservice.service.util;

import hu.vizicsaba.betvictortask.textprocessingservice.service.model.ParagraphProcessDetails;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@UtilityClass
public class ParagraphProcessUtil {

    public String getMostFrequentWord(List<ParagraphProcessDetails> paragraphProcessDetailsList) {
        List<Map<String, Integer>> wordCountMapList = paragraphProcessDetailsList.stream()
                .map(ParagraphProcessDetails::getWordCountMap)
                .collect(Collectors.toList());

        Map<String, Integer> mergedWordCountMap = wordCountMapList.stream()
                .flatMap(wordCountMap -> wordCountMap.entrySet().stream())
                .collect(groupingBy(Map.Entry::getKey, summingInt(Map.Entry::getValue)));

        return Collections.max(mergedWordCountMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Double getAverageParagraphSize(List<ParagraphProcessDetails> paragraphProcessDetailsList) {
        return paragraphProcessDetailsList.stream()
                .map(ParagraphProcessDetails::getParagraphSize)
                .collect(Collectors.averagingDouble(paragraphSize -> paragraphSize));
    }

    public Double getAverageParagraphProcessingTime(List<ParagraphProcessDetails> paragraphProcessDetailsList) {
        return paragraphProcessDetailsList.stream()
                .map(ParagraphProcessDetails::getParagraphProcessingTime)
                .collect(Collectors.averagingDouble(paragraphProcessingTime -> paragraphProcessingTime));
    }
}
