package microBankApp.account_service.controller;


import microBankApp.account_service.service.AccountBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class BalanceController {
    private final AccountBalanceService svc;

    public BalanceController(AccountBalanceService svc) {
        this.svc = svc;
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable UUID id, @RequestBody AccountDtos.AmountRequest req) {
        var a = svc.deposit(id, req.amountMinor());
        return ResponseEntity.ok(new AccountDtos.AccountResponse(a.getId(), a.getAccountNumber(), a.getOwnerName(), a.getBalanceMinor()));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable UUID id, @RequestBody AccountDtos.AmountRequest req) {
        var a = svc.withdraw(id, req.amountMinor());
        return ResponseEntity.ok(new AccountDtos.AccountResponse(a.getId(), a.getAccountNumber(), a.getOwnerName(), a.getBalanceMinor()));
    }
}
