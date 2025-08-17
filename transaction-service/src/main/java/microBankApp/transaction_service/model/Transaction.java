package microBankApp.transaction_service.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TxnType type;

    @Column(columnDefinition = "BINARY(16)")
    private UUID accountId;       // for deposit/withdrawal

    @Column(columnDefinition = "BINARY(16)")
    private UUID fromAccountId;   // for transfer

    @Column(columnDefinition = "BINARY(16)")
    private UUID toAccountId;     // for transfer

    @Column(nullable = false)
    private long amountMinor;

    @Column(nullable = false)
    private Instant createdAt;

    protected Transaction() { }

    public static Transaction deposit(UUID accountId, long amount) {
        var t = new Transaction();
        t.id = UUID.randomUUID();
        t.type = TxnType.DEPOSIT;
        t.accountId = accountId;
        t.amountMinor = amount;
        t.createdAt = Instant.now();
        return t;
    }

    public static Transaction withdrawal(UUID accountId, long amount) {
        var t = new Transaction();
        t.id = UUID.randomUUID();
        t.type = TxnType.WITHDRAWAL;
        t.accountId = accountId;
        t.amountMinor = amount;
        t.createdAt = Instant.now();
        return t;
    }

    public static Transaction transfer(UUID from, UUID to, long amount) {
        var t = new Transaction();
        t.id = UUID.randomUUID();
        t.type = TxnType.TRANSFER;
        t.fromAccountId = from;
        t.toAccountId = to;
        t.amountMinor = amount;
        t.createdAt = Instant.now();
        return t;
    }

    // getters
    public UUID getId() { return id; }
    public TxnType getType() { return type; }
    public UUID getAccountId() { return accountId; }
    public UUID getFromAccountId() { return fromAccountId; }
    public UUID getToAccountId() { return toAccountId; }
    public long getAmountMinor() { return amountMinor; }
    public Instant getCreatedAt() { return createdAt; }
}
