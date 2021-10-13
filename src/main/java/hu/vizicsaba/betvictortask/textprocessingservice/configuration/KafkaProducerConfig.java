package hu.vizicsaba.betvictortask.textprocessingservice.configuration;

import hu.vizicsaba.betvictortask.textprocessingservice.service.model.TextProcessResponse;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, TextProcessResponse> reactiveKafkaProducerTemplate(KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();

        return new ReactiveKafkaProducerTemplate<String, TextProcessResponse>(SenderOptions.create(props));
    }

}
