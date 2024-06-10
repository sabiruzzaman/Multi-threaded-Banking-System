package management;

import db.ConnectionClass;
import utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDetails {

    public AccountDetails() {
    }

    public void accountDetails(Connection con) {
        Utils.println("Please your account ID");
        int accountId = Utils.readInt();

        String query = "SELECT * FROM Accounts WHERE account_id = " + accountId;
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int id = rs.getInt("account_id");
                String name = rs.getString("account_holder_name");
                double balance = rs.getBigDecimal("balance").doubleValue();
                System.out.printf("Account ID: %d\nName: %s\nBalance: %.2f%n", id, name, balance);
            } else {
                System.out.println("No account found with ID: " + accountId);
                accountDetails(ConnectionClass.getInstance());
            }
        } catch (Exception e) {
            Utils.print(e.getMessage());
        }
    }
}
