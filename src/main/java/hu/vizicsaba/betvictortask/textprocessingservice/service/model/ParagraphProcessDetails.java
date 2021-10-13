package hu.vizicsaba.betvictortask.textprocessingservice.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParagraphProcessDetails {

    private Map<String, Integer> wordCountMap;

    private Integer paragraphSize;

    private Long paragraphProcessingTime;

}
