import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount implements Serializable
{
    private static final long serialVersionUID = 14564789845668L;
    private static int nextAccountNum = 1;
    private final int accountNum;
    private final String ownerName;
    private double balance;
    private final List<Transaction> transactions;

    public BankAccount(String ownerName, double balance)
    {
        this.ownerName = ownerName;
        this.balance = balance;
        this.accountNum = nextAccountNum++;
        this.transactions = new ArrayList<>();
    }

    public int getAccountNum()
    {
        return accountNum;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public double getBalance()
    {
        return balance;
    }

    protected void setBalance(double balance)
    {
        this.balance = balance;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void deposit(double amount)
    {
        if (amount <= 0)
        {
            throw new IllegalArgumentException("Invalid amount for deposit: " + amount);
        }
        balance += amount;
        transactions.add(new Transaction(TransactionType.deposit, amount));
    }

    public abstract boolean withdraw(double amount);

    public boolean transfer(BankAccount destinationAccount, double amount)
    {
        if (amount > 0 && balance >= amount)
        {
            setBalance(balance - amount);
            destinationAccount.deposit(amount);
            transactions.add(new Transaction(TransactionType.transfer, amount));
            destinationAccount.getTransactions().add(new Transaction(TransactionType.transfer, amount));
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "BankAccount{" +
                "accountNum=" + accountNum +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
