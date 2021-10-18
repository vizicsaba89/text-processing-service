package hu.vizicsaba.betvictortask.textprocessingservice.service.component.kafka;

import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
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
    private ReactiveKafkaProducerTemplate<String, TextProcessResponse> reactiveKafkaProducerTemplate;

    public void send(TextProcessResponse textProcessResponse) {
        reactiveKafkaProducerTemplate.send(topic, textProcessResponse)
                .subscribe(result -> log.info("data: {}, exception: {}", result.recordMetadata(), result.exception()));
    }

}
