package test_cases;

import db.ConnectionClass;
import management.TransactionManager;

public class TestCaseForTransfer extends Thread {

    @Override
    public void run() {
        TransactionManager tm = new TransactionManager();
        tm.doTransfer(ConnectionClass.getInstance(),10,9,200,3);
    }
}
