package microBankApp.transaction_service.service;

import microBankApp.transaction_service.model.Transaction;
import microBankApp.transaction_service.repository.TransactionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
public class TransactionService {
    private final TransactionRepository repo;
    private final RestTemplate http;

    public TransactionService(TransactionRepository repo, RestTemplate http) {
        this.repo = repo;
        this.http = http;
    }

    private record AmountRequest(long amountMinor) { }

    @Transactional
    public Transaction deposit(UUID accountId, long amount) {
        if (amount <= 0) throw new ResponseStatusException(BAD_REQUEST, "amount must be > 0");
        callAccount(accountId, "deposit", amount);
        return repo.save(Transaction.deposit(accountId, amount));
    }

    @Transactional
    public Transaction withdraw(UUID accountId, long amount) {
        if (amount <= 0) throw new ResponseStatusException(BAD_REQUEST, "amount must be > 0");
        callAccount(accountId, "withdraw", amount);
        return repo.save(Transaction.withdrawal(accountId, amount));
    }

    @Transactional
    public Transaction transfer(UUID from, UUID to, long amount) {
        if (amount <= 0) throw new ResponseStatusException(BAD_REQUEST, "amount must be > 0");
        if (from.equals(to)) throw new ResponseStatusException(BAD_REQUEST, "from and to must differ");

        // 1) withdraw from 'from'
        try {
            callAccount(from, "withdraw", amount);
        } catch (ResponseStatusException ex) {
            // propagate (e.g., insufficient funds or 404)
            throw ex;
        }

        // 2) deposit to 'to' ; if this fails, try to compensate by re-depositing to 'from'
        try {
            callAccount(to, "deposit", amount);
        } catch (ResponseStatusException ex) {
            // compensate
            try { callAccount(from, "deposit", amount); } catch (Exception ignore) {}
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "transfer failed while crediting destination");
        }

        return repo.save(Transaction.transfer(from, to, amount));
    }

    private void callAccount(UUID id, String action, long amount) {
        var url = "http://account-service/accounts/%s/%s".formatted(id, action);
        var req = RequestEntity
                .post(URI.create(url))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AmountRequest(amount));
        try {
            http.exchange(req, Map.class); // ignore body; we only care about status
        } catch (RestClientResponseException ex) {
            HttpStatusCode sc = ex.getStatusCode();
            if (sc.is4xxClientError()) {
                throw new ResponseStatusException(sc.value() == 404 ? NOT_FOUND :
                        sc.value() == 409 ? CONFLICT : BAD_REQUEST, ex.getResponseBodyAsString());
            }
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "account-service unavailable");
        }
    }
}