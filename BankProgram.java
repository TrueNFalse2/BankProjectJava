import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class BankProgram
{
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank;
    private static final String FILENAME = "accounts.dat";

    public static void initializeBank()
    {
        bank = new Bank("MyBank");
        bank.loadAccountsFromFile(FILENAME);
        System.out.println("Loaded accounts: " + bank.getAccounts().size());
    }

    public static void saveBank()
    {
        bank.saveAccountsToFile(FILENAME);
    }

    public static Bank getBank()
    {
        return bank;
    }

    public static String getCurrentDateTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void openAccount()
    {
        String[] options = {"Regular Account", "Credit Account"};
        int accountType = JOptionPane.showOptionDialog(null, "Choose account type", "Open Account",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (accountType == -1) return; // User canceled

        String ownerName = JOptionPane.showInputDialog(null, "Enter Owner's name:");

        if (accountType == 0)
        {
            String balanceStr = JOptionPane.showInputDialog(null, "Enter initial balance:");
            double balance = Double.parseDouble(balanceStr);
            BankAccount newAccount = bank.addRegularAccount(ownerName, balance);
            JOptionPane.showMessageDialog(null, "Account opened successfully. Account Number: " + newAccount.getAccountNum());
        } else if (accountType == 1)
        {
            String balanceStr = JOptionPane.showInputDialog(null, "Enter initial balance:");
            double balance = Double.parseDouble(balanceStr);
            String creditLimitStr = JOptionPane.showInputDialog(null, "Enter credit limit:");
            double creditLimit = Double.parseDouble(creditLimitStr);
            String negBalRateStr = JOptionPane.showInputDialog(null, "Enter negative balance interest rate:");
            double negBalRate = Double.parseDouble(negBalRateStr);
            BankAccount newAccount = bank.addCreditAccount(ownerName, balance, creditLimit, negBalRate);
            JOptionPane.showMessageDialog(null, "Account opened successfully. Account Number: " + newAccount.getAccountNum());
        }
    }

    public static void deposit()
    {
        String accountNumStr = JOptionPane.showInputDialog(null, "Enter account number:");
        int accountNum = Integer.parseInt(accountNumStr);
        String amountStr = JOptionPane.showInputDialog(null, "Enter amount to deposit:");
        double amount = Double.parseDouble(amountStr);
        BankAccount account = bank.findAccountByNumber(accountNum);
        if (account != null)
        {
            account.setBalance(account.getBalance() + amount);
            account.getTransactions().add(new Transaction(TransactionType.deposit, amount));
            JOptionPane.showMessageDialog(null, "Deposit successful. New Balance: " + account.getBalance());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public static void withdraw()
    {
        String accountNumStr = JOptionPane.showInputDialog(null, "Enter account number:");
        int accountNum = Integer.parseInt(accountNumStr);
        String amountStr = JOptionPane.showInputDialog(null, "Enter amount to withdraw:");
        double amount = Double.parseDouble(amountStr);
        BankAccount account = bank.findAccountByNumber(accountNum);
        if (account != null)
        {
            if (account.withdraw(amount))
            {
                JOptionPane.showMessageDialog(null, "Withdrawal successful. New Balance: " + account.getBalance());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Withdrawal failed. Not enough balance.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public static void transfer()
    {
        String sourceAccountNumStr = JOptionPane.showInputDialog(null, "Enter source account number:");
        int sourceAccountNum = Integer.parseInt(sourceAccountNumStr);
        String destAccountNumStr = JOptionPane.showInputDialog(null, "Enter destination account number:");
        int destAccountNum = Integer.parseInt(destAccountNumStr);
        String amountStr = JOptionPane.showInputDialog(null, "Enter amount to transfer:");
        double amount = Double.parseDouble(amountStr);
        BankAccount sourceAccount = bank.findAccountByNumber(sourceAccountNum);
        BankAccount destAccount = bank.findAccountByNumber(destAccountNum);
        if (sourceAccount != null && destAccount != null)
        {
            if (sourceAccount.withdraw(amount))
            {
                destAccount.setBalance(destAccount.getBalance() + amount);
                sourceAccount.getTransactions().add(new Transaction(TransactionType.withdrawal, amount));
                destAccount.getTransactions().add(new Transaction(TransactionType.deposit, amount));
                JOptionPane.showMessageDialog(null, "Transfer successful. New balance of source account: " + sourceAccount.getBalance());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Transfer failed. Not enough balance.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Source or destination account not found.");
        }
    }

    public static void checkBalance()
    {
        String accountNumStr = JOptionPane.showInputDialog(null, "Enter account number:");
        int accountNum = Integer.parseInt(accountNumStr);
        BankAccount account = bank.findAccountByNumber(accountNum);
        if (account != null)
        {
            JOptionPane.showMessageDialog(null, "Account balance: " + account.getBalance());
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public static void viewReports()
    {
        String[] options = {"Accounts by owner", "All accounts", "Credit accounts", "Negative balance accounts"};
        int choice = JOptionPane.showOptionDialog(null, "Choose report type", "View Reports",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice)
        {
            case 0:
                String ownerName = JOptionPane.showInputDialog(null, "Enter owner name:");
                List<BankAccount> accountsByOwner = bank.findAccountByOwner(ownerName);
                StringBuilder ownerReport = new StringBuilder("Report generated on: " + getCurrentDateTime() + "\n\n");
                for (int i = 0; i < accountsByOwner.size(); i++)
                {
                    ownerReport.append(accountsByOwner.get(i)).append("\n");
                }
                JOptionPane.showMessageDialog(null, ownerReport.toString());
                break;
            case 1:
                List<BankAccount> allAccounts = bank.printAllAccounts();
                StringBuilder allReport = new StringBuilder("Report generated on: " + getCurrentDateTime() + "\n\n");
                for (int i = 0; i < allAccounts.size(); i++)
                {
                    allReport.append(allAccounts.get(i)).append("\n");
                }
                JOptionPane.showMessageDialog(null, allReport.toString());
                break;
            case 2:
                List<CreditAccount> creditAccounts = bank.getCreditAccounts();
                StringBuilder creditReport = new StringBuilder("Report generated on: " + getCurrentDateTime() + "\n\n");
                for (int i = 0; i < creditAccounts.size(); i++)
                {
                    creditReport.append(creditAccounts.get(i)).append("\n");
                }
                JOptionPane.showMessageDialog(null, creditReport.toString());
                break;
            case 3:
                List<BankAccount> negativeBalanceAccounts = bank.getNegativeBalanceAccounts();
                StringBuilder negativeReport = new StringBuilder("Report generated on: " + getCurrentDateTime() + "\n\n");
                for (int i = 0; i < negativeBalanceAccounts.size(); i++)
                {
                    negativeReport.append(negativeBalanceAccounts.get(i)).append("\n");
                }
                JOptionPane.showMessageDialog(null, negativeReport.toString());
                break;
            default:
                break;
        }
    }

    public static void removeAccount(int accountNum)
    {
        BankAccount account = bank.findAccountByNumber(accountNum);
        if (account != null) {
            bank.removeAccount(accountNum);
            JOptionPane.showMessageDialog(null, "Account removed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public static void main(String[] args)
    {
        initializeBank();
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new GUIBank();
            frame.setVisible(true);
            frame.addWindowListener(new java.awt.event.WindowAdapter()
            {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent)
                {
                    saveBank();
                }
            });
        });
    }
}
