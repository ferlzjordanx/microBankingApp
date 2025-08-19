package microBankApp.transaction_service.service;

import microBankApp.transaction_service.model.TransactionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventPublisher {
    private final KafkaTemplate<String, TransactionEvent> template;
    private final String topic;

    public TransactionEventPublisher(
            KafkaTemplate<String, TransactionEvent> template,
            @Value("${app.kafka.topic.transactions}") String topic) {
        this.template = template;
        this.topic = topic;
    }

    public void publish(TransactionEvent evt) {
        template.send(topic, evt.getId(), evt);
    }
}