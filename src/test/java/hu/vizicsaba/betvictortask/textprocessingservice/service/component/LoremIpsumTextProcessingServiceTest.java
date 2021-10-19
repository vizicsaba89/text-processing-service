package hu.vizicsaba.betvictortask.textprocessingservice.service.component;

import hu.vizicsaba.betvictortask.textprocessingservice.data.repository.TextProcessingRepository;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessRequest;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka
public class LoremIpsumTextProcessingServiceTest {

    @MockBean
    private TextProcessingRepository textProcessingRepository;

    @Autowired
    private LoremIpsumTextProcessingService loremIpsumTextProcessingService;

    @BeforeEach
    void setup() {
        Mockito.when(textProcessingRepository.getTextResponseData(any(Integer.class), any(String.class)))
                .thenReturn(Mono.just(getLoremIpsumText()));
    }

    @Test
    @DisplayName("LoremIpsumTextProcessingService -> get processed text response")
    void givenMethodToProcessText_whenMethodCalled_thenProcessedTextResponseShouldReturned() {
        Mono<TextProcessResponse> loremIpsumTextProcessingServiceMono = loremIpsumTextProcessingService.processText(new TextProcessRequest(1, 3, "short"));

        StepVerifier.create(loremIpsumTextProcessingServiceMono)
                .expectNextMatches(response -> response != null && "satis".equals(response.getMostFrequentWord()))
                .expectComplete()
                .verify();
    }

    private String getLoremIpsumText() {
        return "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mihi enim satis est, " +
                "ipsis non satis. Poterat autem inpune; Illa videamus, quae a te de amicitia dicta sunt. Duo Reges: constructio interrete. " +
                "Quid dubitas igitur mutare principia naturae? Non est ista, inquam, Piso, magna dissensio. Restatis igitur vos; Murenam te accusante defenderem. </p>\n" +
                "\n" +
                "<p>Ut id aliis narrare gestiant? Habent enim et bene longam et satis litigiosam disputationem. " +
                "Teneo, inquit, finem illi videri nihil dolere. Sed nunc, quod agimus; Iam id ipsum absurdum, maximum malum neglegi. " +
                "Paria sunt igitur. Efficiens dici potest. Nos cum te, M. </p>\n" +
                "\n" +
                "<p>Nunc omni virtuti vitium contrario nomine opponitur. Proclivi currit oratio. Mihi enim satis est, ipsis non satis. " +
                "Et ille ridens: Video, inquit, quid agas; Istic sum, inquit. Sumenda potius quam expetenda. </p>";
    }

}
