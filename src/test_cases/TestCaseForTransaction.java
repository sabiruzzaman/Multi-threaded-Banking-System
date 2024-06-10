package test_cases;

import db.ConnectionClass;
import management.TransactionManager;

public class TestCaseForTransaction extends Thread {
    @Override
    public void run() {
        TransactionManager tm = new TransactionManager();
        tm.doTransaction(ConnectionClass.getInstance(), 10, 2, 10);
    }
}
