package com.pik.backend;

import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED;

public class SpringTransactionProvider implements TransactionProvider {

    @Autowired
    DataSourceTransactionManager transactionManager;

    @Override
    public void begin(TransactionContext ctx) {
        // This TransactionProvider behaves like jOOQ's DefaultTransactionProvider,
        // which supports nested transactions using Savepoints
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition(PROPAGATION_NESTED));
        ctx.transaction(new SpringTransaction(tx));
    }

    @Override
    public void commit(TransactionContext ctx) {
        transactionManager.commit(((SpringTransaction) ctx.transaction()).tx);
    }

    @Override
    public void rollback(TransactionContext ctx) {
        transactionManager.rollback(((SpringTransaction) ctx.transaction()).tx);
    }
}