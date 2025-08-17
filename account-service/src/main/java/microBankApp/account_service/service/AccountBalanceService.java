package microBankApp.account_service.service;


import microBankApp.account_service.model.Account;
import microBankApp.account_service.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
public class AccountBalanceService {
    private final AccountRepository repo;

    public AccountBalanceService(AccountRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Account deposit(UUID id, long amountMinor) {
        if (amountMinor <= 0) throw new ResponseStatusException(BAD_REQUEST, "amount must be > 0");
        var acc = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "account not found"));
        acc.setBalanceMinor(acc.getBalanceMinor() + amountMinor);
        return repo.save(acc);
    }

    @Transactional
    public Account withdraw(UUID id, long amountMinor) {
        if (amountMinor <= 0) throw new ResponseStatusException(BAD_REQUEST, "amount must be > 0");
        var acc = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "account not found"));
        if (acc.getBalanceMinor() < amountMinor) {
            throw new ResponseStatusException(CONFLICT, "insufficient funds");
        }
        acc.setBalanceMinor(acc.getBalanceMinor() - amountMinor);
        return repo.save(acc);
    }
}

