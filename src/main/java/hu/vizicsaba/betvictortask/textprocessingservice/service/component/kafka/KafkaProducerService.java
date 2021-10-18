package hu.vizicsaba.betvictortask.textprocessingservice.service.component.kafka;

import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {

    @Value(value = "${configuration.kafka.topic}")
    private String topic;

    @Autowired
    private ReactiveKafkaProducerTemplate<String, TextProcessResult> reactiveKafkaProducerTemplate;

    public void send(TextProcessResponse textProcessResponse) {
        reactiveKafkaProducerTemplate.send(topic, getTextProcessResult(textProcessResponse))
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", textProcessResponse, senderResult.recordMetadata().offset()))
                .subscribe();
    }

    private TextProcessResult getTextProcessResult(TextProcessResponse textProcessResponse) {
        return new TextProcessResult(textProcessResponse.getMostFrequentWord(), textProcessResponse.getAverageParagraphSize(), textProcessResponse.getAverageParagraphProcessingTime(), textProcessResponse.getTotalProcessingTime());
    }

}
