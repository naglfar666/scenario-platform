package org.example.router.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {

    @Value("${kafka.outTopicName}")
    private String outTopicName;

    @Value("${kafka.inTopicName}")
    private String inTopicName;

    @Bean
    public NewTopic routerOutTopic() {
        return new NewTopic(outTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic routerIncomeTopic() {
        return new NewTopic(inTopicName, 1, (short) 1);
    }

}
