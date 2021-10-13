package hu.vizicsaba.betvictortask.textprocessingservice.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextProcessRequest {

    private Integer startNumberOfParagraphs;

    private Integer endNumberOfParagraphs;

    private String averageLengthOfParagraph;

}
