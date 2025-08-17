package microBankApp.transaction_service.controller;

import microBankApp.transaction_service.model.Transaction;
import microBankApp.transaction_service.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TxnController {
    private final TransactionService svc;
    public TxnController(TransactionService svc) { this.svc = svc; }

    public record TxnRequest(UUID accountId, UUID fromAccountId, UUID toAccountId, long amountMinor) {}
    public record TxnResponse(UUID id, String type, UUID accountId, UUID fromAccountId, UUID toAccountId, long amountMinor) {}

    @PostMapping("/deposit")
    public ResponseEntity<TxnResponse> deposit(@RequestBody TxnRequest req) {
        var t = svc.deposit(req.accountId(), req.amountMinor());
        return ResponseEntity.ok(toDto(t));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TxnResponse> withdraw(@RequestBody TxnRequest req) {
        var t = svc.withdraw(req.accountId(), req.amountMinor());
        return ResponseEntity.ok(toDto(t));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TxnResponse> transfer(@RequestBody TxnRequest req) {
        var t = svc.transfer(req.fromAccountId(), req.toAccountId(), req.amountMinor());
        return ResponseEntity.ok(toDto(t));
    }

    private TxnResponse toDto(Transaction t) {
        return new TxnResponse(t.getId(), t.getType().name(), t.getAccountId(),
                t.getFromAccountId(), t.getToAccountId(), t.getAmountMinor());
    }
}