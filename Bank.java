import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Bank implements Serializable
{
    private static final long serialVersionUID = 14564789845668L;
    private String name;
    private List<BankAccount> accounts;

    public Bank(String name)
    {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public BankAccount addRegularAccount(String ownerName, double balance)
    {
        BankAccount newAccount = new RegularAccount(ownerName, balance);
        accounts.add(newAccount);
        return newAccount;
    }

    public BankAccount addCreditAccount(String ownerName, double balance, double creditLimit, double negBalRate)
    {
        BankAccount newAccount = new CreditAccount(ownerName, balance, creditLimit, negBalRate);
        accounts.add(newAccount);
        return newAccount;
    }

    public BankAccount addSavingsAccount(String ownerName)
    {
        BankAccount newAccount = new SavingsAccount(ownerName);
        accounts.add(newAccount);
        return newAccount;
    }

    public BankAccount findAccountByNumber(int accountNum)
    {
        for (int i = 0; i < accounts.size(); i++)
        {
            BankAccount account = accounts.get(i);
            if (account.getAccountNum() == accountNum)
            {
                return account;
            }
        }
        return null;
    }

    public boolean removeAccount(int accountNum)
    {
        for (int i = 0; i < accounts.size(); i++)
        {
            if (accounts.get(i).getAccountNum() == accountNum)
            {
                accounts.remove(i);
                return true;
            }
        }
        return false;
    }

    public void saveAccountsToFile(String fileName)
    {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(accounts);
            System.out.println("Accounts saved to file: " + fileName);
        }
        catch (IOException e)
        {
            System.err.println("Error saving accounts to file: " + e.getMessage());
        }
    }

    public void loadAccountsFromFile(String fileName)
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            System.err.println("Error: The file " + fileName + " does not exist. Initializing with an empty account list.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            accounts = (List<BankAccount>) ois.readObject();
            System.out.println("Accounts loaded from file: " + fileName);
        } catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error loading accounts from file: " + e.getMessage());
        }
    }

    public List<BankAccount> getAccounts()
    {
        return accounts;
    }

    public List<BankAccount> findAccountByOwner(String ownerName)
    {
        List<BankAccount> result = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++)
        {
            BankAccount account = accounts.get(i);
            if (account.getOwnerName().equalsIgnoreCase(ownerName))
            {
                result.add(account);
            }
        }
        return result;
    }

    public List<CreditAccount> getCreditAccounts()
    {
        List<CreditAccount> result = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++)
        {
            BankAccount account = accounts.get(i);
            if (account instanceof CreditAccount)
            {
                result.add((CreditAccount) account);
            }
        }
        return result;
    }

    public List<BankAccount> getNegativeBalanceAccounts()
    {
        List<BankAccount> result = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount account = accounts.get(i);
            if (account.getBalance() < 0) {
                result.add(account);
            }
        }
        return result;
    }

    public List<BankAccount> printAllAccounts()
    {
        for (int i = 0; i < accounts.size(); i++)
        {
            System.out.println(accounts.get(i));
        }
        return accounts;
    }
}
