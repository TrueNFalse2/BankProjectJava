import java.io.Serializable;

public class CreditAccount extends BankAccount implements Serializable
{
    private static final long serialVersionUID = 11362754876668L;
    private double creditLimit;
    private double negBalRate;

    public CreditAccount(String ownerName, double balance, double creditLimit, double negBalRate)
    {
        super(ownerName, balance);
        this.creditLimit = creditLimit;
        this.negBalRate = negBalRate;
    }

    @Override
    public boolean withdraw(double amount)
    {
        double balanceWithCredit = getBalance() + creditLimit;
        if (amount <= 0)
        {
            throw new IllegalArgumentException("Invalid amount for withdrawal: " + amount);
        }
        if (amount > balanceWithCredit)
        {
            return false;
        }
        setBalance(getBalance() - amount);
        getTransactions().add(new Transaction(TransactionType.withdrawal, amount));
        return true;
    }

    public double getCreditLimit()
    {
        return creditLimit;
    }

    public double getNegBalRate()
    {
        return negBalRate;
    }

    public void setNegBalRate(double negBalRate)
    {
        this.negBalRate = negBalRate;
    }
}
