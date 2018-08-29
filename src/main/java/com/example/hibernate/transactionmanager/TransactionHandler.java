package com.example.hibernate.transactionmanager;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.example.hibernate.datasource.DatabaseProduct;

/**
 * Class demacrates the transaction and handles exception and actual transaction events.
 * @author amit
 *
 */
public class TransactionHandler {

	/**
	 * Implements the execute around pattern to demacrate the transactions and handle commit, rollback and exceptions
	 * @param transaction Code to be executed in transaction
	 * @return result of executing transaction if any
	 * @throws Exception Error occurred while executing/commiting/rollback transaction.
	 */
	public static <T> T executeInTransaction(Transaction<T> transaction) throws Exception {
		UserTransaction tx = null;
		try {
			// Create UserTransaction
			TransactionManagerSetup transactionManager = TransactionManagerSetup.getInstance(DatabaseProduct.POSTGRES);
			tx = transactionManager.getUserTransaction();	
			
			// Begin transaction
			tx.begin();

			// Create entity manager and associate it to user transaction attached to current thread of excution
			EntityManager em = Persistence.createEntityManagerFactory("JPADB").createEntityManager();
			
			// Execute transaction code by client
			T result = transaction.execute(em);

			// commit
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rollback(tx);
			} catch (Exception ex) {
				System.err.println("Rollback of transaction failed, trace follows!");
				ex.printStackTrace(System.err);
				throw ex;
			}
			throw e;
		}
	}

	/**
	 * This is the exact correct way to rollback transactions
	 * @param tx UserTransactin to rollback
	 * @throws Exception exception occurred while rollback of exception.
	 */
	private static void rollback(final UserTransaction tx) throws Exception {
		if (tx != null && (tx.getStatus() == Status.STATUS_ACTIVE || tx.getStatus() == Status.STATUS_MARKED_ROLLBACK)) {
			System.out.println("Rolling back the transaction");
			tx.rollback();
		}
	}
}
