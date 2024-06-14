import java.io.Serializable;

public class SavingsAccount extends BankAccount implements Serializable
{
    private static final long serialVersionUID = 15264489845985L;

    public SavingsAccount(String owner)
    {
        super(owner, 0.0);  // Assuming a SavingsAccount has no initial balance
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
