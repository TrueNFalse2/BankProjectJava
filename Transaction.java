import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable
{
    private static final long serialVersionUID = 14562784846668L;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime time;


    public Transaction(TransactionType type, double amount)
    {
        this.type = type;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public TransactionType getType()
    {
        return type;
    }

    public double getAmount()
    {
        return amount;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return "Transaction{" +
                "type=" + type +
                ", amount=" + amount +
                ", timestamp=" + time +
                '}';
    }
}
