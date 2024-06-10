package management;

import utils.Utils;

import java.sql.Connection;
import java.sql.Statement;

public class CreateAccount {

    public CreateAccount() {
    }

    public void creatAccount(Connection con) {
        Statement stmt;
        Utils.println("Enter account holder name");
        String name = Utils.readLine();
        Utils.println("Enter initial balance [initial balance up to 50]");

        double balance;
        do {
            balance = Utils.readDouble();
            if (balance < 50) {
                Utils.println("Invalid balance. Please enter a valid balance");
            }
        } while (!(balance >= 49));

        try {
            String query = String.format("INSERT INTO accounts(account_holder_name,balance) values('%s','%f');", name, balance);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            Utils.println("Create Accounts successfully!");
        } catch (Exception e) {
            Utils.println(e.getMessage());
        }

    }

}
