package hu.vizicsaba.betvictortask.textprocessingservice.configuration;

import hu.vizicsaba.betvictortask.textprocessingservice.service.model.kafka.TextProcessResult;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, TextProcessResult> reactiveKafkaProducerTemplate(KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();

        return new ReactiveKafkaProducerTemplate<String, TextProcessResult>(SenderOptions.create(props));
    }

}
