package microBankApp.account_service.controller;


import microBankApp.account_service.model.Account;
import microBankApp.account_service.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository repo;

    public AccountController(AccountRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<AccountDtos.AccountResponse> create(@RequestBody AccountDtos.CreateAccountRequest req) {
        var saved = repo.save(new Account(req.ownerName()));
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping
    public List<AccountDtos.AccountResponse> list() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDtos.AccountResponse> get(@PathVariable UUID id) {
        return repo.findById(id).map(a -> ResponseEntity.ok(toDto(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    private AccountDtos.AccountResponse toDto(Account a) {
        return new AccountDtos.AccountResponse(a.getId(), a.getAccountNumber(), a.getOwnerName(), a.getBalanceMinor());
    }
}

