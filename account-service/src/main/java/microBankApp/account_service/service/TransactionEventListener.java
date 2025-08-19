package microBankApp.account_service.service;

import microBankApp.transaction_service.model.TransactionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventListener {

    @KafkaListener(topics = "${app.kafka.topic.transactions}", groupId = "account-service")
    public void onEvent(TransactionEvent evt) {
        // For Stage 6: just log it
        System.out.println(">>> [account-service] received event: " + evt.getType()
                + " account=" + evt.getAccountId()
                + " amount=" + evt.getAmountMinor());
        // (Later you could reconcile, audit, etc.)
    }
}
