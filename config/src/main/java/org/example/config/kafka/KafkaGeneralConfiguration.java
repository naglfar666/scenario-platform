package org.example.config.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@Import({ConsumerGeneralConfiguration.class, ProducerGeneralConfiguration.class, TopicGeneralConfiguration.class})
public class KafkaGeneralConfiguration {
}
