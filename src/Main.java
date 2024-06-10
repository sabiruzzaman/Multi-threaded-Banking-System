import db.ConnectionClass;
import management.AccountsManagement;
import test_cases.TestCaseForTransfer;
import test_cases.TestCaseForTransaction;

public class Main {
    public static void main(String[] args) {

        // Create connection to DB
        ConnectionClass.getInstance();

        // Database design accounts and transactions table

/*      CreateTable createTable = new CreateTable(ConnectionClass.getInstance());
        createTable.createAccountsTable("accounts");
        createTable.createTransactionsTable("transactions");*/

        // Dashboard
        AccountsManagement accountsManagement = new AccountsManagement();
        accountsManagement.accountManagement(true);

/*
        TestCaseForTransaction transfer = new TestCaseForTransaction();
        transfer.start();

        TestCaseForTransfer transaction = new TestCaseForTransfer();
        transaction.start();*/


    }


}
