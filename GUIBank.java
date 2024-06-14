import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIBank extends JFrame {
    public GUIBank() {
        setTitle("Bank Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton openAccountButton = new JButton("Open Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton viewReportsButton = new JButton("View Reports");
        JButton viewRecentTransactionsButton = new JButton("View Recent Transactions");
        JButton removeAccountButton = new JButton("Remove Account");
        JButton exitButton = new JButton("Exit");

        openAccountButton.addActionListener(e -> BankProgram.openAccount());
        depositButton.addActionListener(e -> BankProgram.deposit());
        withdrawButton.addActionListener(e -> BankProgram.withdraw());
        transferButton.addActionListener(e -> BankProgram.transfer());
        checkBalanceButton.addActionListener(e -> BankProgram.checkBalance());
        viewReportsButton.addActionListener(e -> BankProgram.viewReports());
        viewRecentTransactionsButton.addActionListener(e -> {
            String accountNumStr = JOptionPane.showInputDialog(null, "Enter account number:");
            if (accountNumStr != null && !accountNumStr.isEmpty()) {
                try {
                    int accountNum = Integer.parseInt(accountNumStr);
                    BankAccount account = BankProgram.getBank().findAccountByNumber(accountNum);
                    if (account != null) {
                        List<Transaction> transactions = account.getTransactions();
                        int start = Math.max(transactions.size() - 5, 0);
                        StringBuilder transactionReport = new StringBuilder();
                        for (int i = start; i < transactions.size(); i++) {
                            transactionReport.append(transactions.get(i)).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, transactionReport.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Account not found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid account number.");
                }
            }
        });
        removeAccountButton.addActionListener(e -> {
            String accountNumStr = JOptionPane.showInputDialog(null, "Enter account number to remove:");
            if (accountNumStr != null && !accountNumStr.isEmpty()) {
                try {
                    int accountNum = Integer.parseInt(accountNumStr);
                    BankProgram.removeAccount(accountNum);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid account number.");
                }
            }
        });
        exitButton.addActionListener(e -> {
            BankProgram.saveBank();
            System.exit(0);
        });

        panel.add(openAccountButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transferButton);
        panel.add(checkBalanceButton);
        panel.add(viewReportsButton);
        panel.add(viewRecentTransactionsButton);
        panel.add(removeAccountButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        BankProgram.initializeBank();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GUIBank();
            frame.setVisible(true);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    BankProgram.saveBank();
                }
            });
        });
    }
}
