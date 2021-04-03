package org.example.router.configuration.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfiguration {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.outTopicName}")
    private String outTopicName;

    @Value("${kafka.inTopicName}")
    private String inTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic routerOutTopic() {
        return new NewTopic(outTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic routerIncomeTopic() {
        return new NewTopic(inTopicName, 1, (short) 1);
    }

}
