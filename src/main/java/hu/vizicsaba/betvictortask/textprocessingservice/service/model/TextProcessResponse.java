package hu.vizicsaba.betvictortask.textprocessingservice.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TextProcessResponse {

    @JsonProperty("most_frequent_word")
    private String mostFrequentWord;

    @JsonProperty("avg_paragraph_size")
    private Integer averageParagraphSize;

    @JsonProperty("avg_paragraph_processing_time")
    private Double averageParagraphProcessingTime;

    @JsonProperty("total_processing_time")
    private Long totalProcessingTime;

}
