package management;

import utils.Utils;

import java.sql.*;


public class TransactionManager {

    public static AccountsManagement accountsManagement = new AccountsManagement();

    private static final Object lock = new Object();

    public void transactionsManager(Connection con) {
        Utils.println("Choose the transaction type");
        Utils.println("---------------------------------------------");
        Utils.println("For Deposits -> Press 1, For Withdrawals -> Press 2, For Transfers -> Press 3, Back Home -> Press 0");

        // invalid option check
        int transactionType;
        do {
            transactionType = Utils.readInt();
            if (transactionType < 0 || transactionType > 3) {
                Utils.println("Please enter a valid option");
            }
        } while (!(transactionType == 0 || transactionType == 1 || transactionType == 2 || transactionType == 3));

        if (transactionType == 0) {
            accountsManagement.accountManagement(false);
            return;
        }

        // read account id for deposit or Withdrawals
        Utils.println("Please enter your account ID");
        int accountId = Utils.readInt();
        Utils.println("Please enter the amount to " + Utils.transactionType(transactionType));

        // negative amount check
        double amount;
        do {
            amount = Utils.readDouble();
            if (amount <= 0) {
                Utils.println("Invalid amount. Please enter a valid amount");
            }
        } while (amount <= 0);

        if (transactionType == 3) {
            Utils.println("Please enter the receiver account ID");
            int receiverId = Utils.readInt();
            doTransfer(con, accountId, receiverId, amount, transactionType);
        } else {
            doTransaction(con, accountId, transactionType, amount);
        }
    }

    public synchronized void doTransaction(Connection con, int accountId, int transactionType, double amount) {
        synchronized (lock) {
            String updateQuery = "";
            String transactionTypeStr = Utils.transactionType(transactionType);

            if (transactionType == 1) {
                updateQuery = "UPDATE accounts SET balance = balance + " + amount + " WHERE account_id = " + accountId;
            } else if (transactionType == 2) {
                updateQuery = "UPDATE accounts SET balance = balance - " + amount + " WHERE account_id = " + accountId;
            }

            try {
                con.setAutoCommit(false);

                double senderBalance = getCurrentBalance(con, accountId);
                if (senderBalance - amount < 50) {
                    Utils.println(Utils.transactionType(transactionType) + " failed: Insufficient funds. Your current balance is: " + senderBalance);
                    return;
                }

                // Update account balance
                try (PreparedStatement updateSenderStmt = con.prepareStatement(updateQuery)) {
                    updateSenderStmt.executeUpdate();
                }


                // Insert transaction record
                String insertQuery = String.format("INSERT INTO transactions (account_id,transaction_type,amount,timestamp) values('%s','%s','%f','%s');", accountId, Utils.transactionType(transactionType), amount, new Timestamp(System.currentTimeMillis()));
                try {
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(insertQuery);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                con.commit();
                Utils.println(transactionTypeStr + " completed successfully!");

            } catch (SQLException e) {
                try {
                    if (con != null) {
                        con.rollback();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                Utils.println("Transaction failed: " + e.getMessage());
            } finally {
                try {
                    if (con != null) {
                        con.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void doTransfer(Connection con, int senderId, int receiverId, double amount, int transactionType) {
        synchronized (lock) {
            String updateSenderQuery = "UPDATE accounts SET balance = balance - " + amount + " WHERE account_id = " + senderId;
            String updateReceiverQuery = "UPDATE accounts SET balance = balance + " + amount + " WHERE account_id = " + receiverId;

            try {
                con.setAutoCommit(false);

                double senderBalance = getCurrentBalance(con, senderId);
                if (senderBalance - amount < 50) {
                    Utils.println(Utils.transactionType(transactionType) + " failed: Insufficient funds. Your current balance is: " + senderBalance);
                    return;
                }

                // Update sender account
                try (PreparedStatement updateSenderStmt = con.prepareStatement(updateSenderQuery)) {
                    updateSenderStmt.executeUpdate();
                }

                // Update receiver account
                try (PreparedStatement updateReceiverStmt = con.prepareStatement(updateReceiverQuery)) {
                    updateReceiverStmt.executeUpdate();
                }


                // Insert sender transaction record
                String insertSenderQuery = String.format("INSERT INTO transactions (account_id,transaction_type,amount,timestamp) values('%s','%s','%f','%s');", senderId, Utils.transactionType(transactionType), amount, new Timestamp(System.currentTimeMillis()));
                try (PreparedStatement insertSenderStmt = con.prepareStatement(insertSenderQuery)) {
                    insertSenderStmt.executeUpdate();
                }

                // Insert receiver transaction record
                String insertReceiverQuery = String.format("INSERT INTO transactions (account_id,transaction_type,amount,timestamp) values('%s','%s','%f','%s');", receiverId, Utils.transactionType(transactionType), amount, new Timestamp(System.currentTimeMillis()));
                try (PreparedStatement insertReceiverStmt = con.prepareStatement(insertReceiverQuery)) {
                    insertReceiverStmt.executeUpdate();
                }

                con.commit();
                Utils.println(Utils.transactionType(transactionType) + " completed successfully!");

            } catch (SQLException e) {
                try {
                    con.setAutoCommit(true);
                    if (con != null) {
                        con.rollback();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                Utils.println("Transfer failed: " + e.getMessage());
            } finally {
                try {
                    if (con != null) {
                        con.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private double getCurrentBalance(Connection con, int accountId) {
        double balance = 0;
        String query = "SELECT balance FROM accounts WHERE  account_id = " + accountId;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                balance = rs.getDouble("balance");
                return balance;
            }
        } catch (Exception e) {
            Utils.print(e.getMessage());
        }
        return balance;
    }
}


