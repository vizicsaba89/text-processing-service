package hu.vizicsaba.betvictortask.textprocessingservice.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextProcessResponse {

    @JsonProperty("most_frequent_word")
    private String mostFrequentWord;

    @JsonProperty("avg_paragraph_size")
    private Double averageParagraphSize;

    @JsonProperty("avg_paragraph_processing_time")
    private Double averageParagraphProcessingTime;

    @JsonProperty("total_processing_time")
    private Long totalProcessingTime;

}
