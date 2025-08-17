package microBankApp.account_service.controller;

import java.util.UUID;

public class AccountDtos {

    public record CreateAccountRequest(String ownerName) {}
    public record AccountResponse(UUID id, String accountNumber, String ownerName, long balanceMinor) {}
    public record AmountRequest(long amountMinor) { }
}
