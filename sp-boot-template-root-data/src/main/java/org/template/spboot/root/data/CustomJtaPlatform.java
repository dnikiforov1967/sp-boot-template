/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

/**
 *
 * @author dima
 */
public class CustomJtaPlatform extends AbstractJtaPlatform {

    private static TransactionManager transactionManager;
    private static UserTransaction transaction;
 
    public static void setTransactionManager(TransactionManager tm) {
        transactionManager = tm;
    }

    public static void setUserTransaction(UserTransaction ut) {
        transaction = ut;
    }
 
    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return transaction;
    }

}
