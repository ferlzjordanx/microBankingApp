package microBankApp.account_service.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 32)
    private String accountNumber;

    @Column(nullable = false, length = 100)
    private String ownerName;

    // store money in minor units (cents)
    @Column(nullable = false)
    private long balanceMinor;

    protected Account() {} // JPA

    public Account(String ownerName) {
        this.id = UUID.randomUUID();
        this.accountNumber = "AC-" + this.id.toString().substring(0, 8).toUpperCase();
        this.ownerName = ownerName;
        this.balanceMinor = 0L;
    }

    // getters/setters
    public UUID getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalanceMinor() { return balanceMinor; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setBalanceMinor(long balanceMinor) { this.balanceMinor = balanceMinor; }
}

