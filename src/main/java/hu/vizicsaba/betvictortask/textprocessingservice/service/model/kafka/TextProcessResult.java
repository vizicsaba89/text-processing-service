package hu.vizicsaba.betvictortask.textprocessingservice.service.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextProcessResult {

    private String mostFrequentWord;

    private Double averageParagraphSize;

    private Double averageParagraphProcessingTime;

    private Long totalProcessingTime;

}
