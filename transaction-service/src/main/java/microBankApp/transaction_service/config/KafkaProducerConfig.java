package microBankApp.transaction_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaProducerConfig {
    @Bean
    public NewTopic transactionTopic(@Value("${app.kafka.topic.transactions}") String topic) {
        return TopicBuilder.name(topic).partitions(1).replicas(1).build();
    }

}
