package management;

import db.ConnectionClass;
import utils.Utils;

public class AccountsManagement {

    private boolean showWelcomeMessage;

    public AccountsManagement() {}

    CreateAccount createAccount = new CreateAccount();
    AccountDetails accountDetails = new AccountDetails();
    TransactionManager transactionManager = new TransactionManager();


    public void accountManagement(boolean showWelcomeMessage) {
        // create account = 1, account details = 2,  deposits = 3, withdrawals = 4, transfers = 5

        if (showWelcomeMessage) {
            Utils.println("");
            Utils.println("Welcome to the Multi-threaded Banking System!");
            Utils.println("---------------------------------------------");
        }

        Utils.println("Create Accounts -> Press 1");
        Utils.println("Account Details -> Press 2");
        Utils.println("Transactions  -> Press 3");
        Utils.println("Exit -> Press 0");

        int key = Utils.readInt();

        if (key == 0) {
            Utils.println("Thank you for using Multi-threaded Banking System!");
        } else {
            if (key > 0 && key <= 3) {
                if (key == 1) {
                    createAccount.creatAccount(ConnectionClass.getInstance());
                } else if (key == 2) {
                    accountDetails.accountDetails(ConnectionClass.getInstance());
                } else {
                    transactionManager.transactionsManager(ConnectionClass.getInstance());
                }
            } else {
                Utils.println("Please enter a valid option");
                Utils.println("---------------------------------------------");
                accountManagement(false);
            }
        }
    }


}
