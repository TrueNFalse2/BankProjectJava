# 🏦 Bank Management System

A Java-based bank management system with GUI that allows users to create and manage different types of bank accounts: Regular, Credit, and Savings. The application supports deposits, withdrawals, transfers, transaction history, and data persistence.

## 🚀 Features

- Add and remove accounts (Regular, Credit, Savings)
- Deposit and withdraw funds
- Transfer money between accounts
- View account details and recent transactions
- Generate reports:
  - All accounts
  - Accounts by owner
  - Credit accounts
  - Accounts with negative balances
- Save/load account data to/from a file (`accounts.dat`)
- GUI interface using Swing

## 🧱 Technologies

- Java (OOP)
- Java Swing (GUI)
- Serialization (saving/loading objects to file)

## 📂 Project Structure

Bank.java --> Core bank system logic BankAccount.java --> Abstract class for accounts RegularAccount.java --> Regular account implementation CreditAccount.java --> Credit account with limit and interest SavingsAccount.java --> Savings account (starts with 0 balance) Transaction.java --> Transaction class (type, amount, date) TransactionType.java --> Enum for transaction types BankProgram.java --> Main program logic GUIBank.java --> Graphical user interface accounts.dat --> Auto-generated file for saved accounts

## ▶️ How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/BankProjectJava.git
   cd BankProjectJava

2. Compile the Java files:
javac *.java

3. Run the application:
java GUIBank

💾 Data Persistence
All accounts and transactions are automatically saved to accounts.dat when the program closes, and reloaded when it starts again.

📷 Screenshots
Add GUI screenshots here (optional)

📌 Notes
Requires Java 8 or higher

To reset the system, delete the accounts.dat file

📄 License
This project is open-source and free to use for learning and personal projects.

---

Let me know if you'd like a version with images, clickable badges (e.g., Java version), or hosted GitHub Pages as a demo!






