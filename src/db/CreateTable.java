package db;

import utils.Utils;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {

    Connection con;
    String tableName;


    public CreateTable(Connection connection) {
        this.con = connection;
    }

    public void createAccountsTable(String tableName) {
        Statement stmt;

        try {
            String query = "CREATE TABLE " + tableName + " (account_id SERIAL PRIMARY KEY,account_holder_name VARCHAR(255),balance DECIMAL);";
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            Utils.println("Table has been created successfully !!");
        } catch (Exception e) {
            Utils.println(e.getMessage());
        }
    }

    public void createTransactionsTable(String tableName) {
        Statement stmt;

        try {
            String query = "CREATE TABLE " + tableName + " (transaction_id SERIAL PRIMARY KEY,account_id INT,transaction_type VARCHAR(50) CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),amount DECIMAL,timestamp TIMESTAMP,FOREIGN KEY (account_id) REFERENCES accounts(account_id));";
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            Utils.println("Table has been created successfully !!");
        } catch (Exception e) {
            Utils.println(e.getMessage());
        }
    }

}
