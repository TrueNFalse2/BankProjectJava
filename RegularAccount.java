import java.io.Serializable;

public class RegularAccount extends BankAccount implements Serializable
{
    private static final long serialVersionUID = 1L;

    public RegularAccount(String ownerName, double balance)
    {
        super(ownerName, balance);
    }

    @Override
    public boolean withdraw(double amount)
    {
        if (amount > getBalance())
        {
            System.out.println("Insufficient balance.");
            return false;
        }
        else
        {
            setBalance(getBalance() - amount);
            getTransactions().add(new Transaction(TransactionType.withdrawal, amount));
            return true;
        }
    }
}
