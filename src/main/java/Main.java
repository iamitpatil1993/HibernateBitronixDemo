import com.example.hibernate.transactionmanager.TransactionHandler;

public class Main {
	public static void main(String[] args) throws Exception {

		// Transaction 1
		TransactionHandler.executeInTransaction(entityManager -> {
			HelloWorldEntity entity = new HelloWorldEntity();
			entityManager.persist(entity);
			return null;
		});

		// Transaction 2
		HelloWorldEntity transactionResult = TransactionHandler.executeInTransaction(entityManager -> {
			HelloWorldEntity entity = new HelloWorldEntity();
			entityManager.persist(entity);
			return entity;
		});
		System.out.println("Transaction result :: " + transactionResult);

		// Transaction 3
		TransactionHandler.executeInTransaction(entityManager -> {
			throw new Exception("Something went wrong logically"); 
			/*
			 *	 This should rollback the transaction, I don't know whether this JTA provider
				can do it automatically, but currently, I have managed it(using execute around pattern) to rollback the transaction using custom exception handling and rollback code.
				// TODO need to check can JTA transaction manager automatically rollback the transaction in JAVA SE  where w have demacrated the transaction
				in programmatic way and not delcarative way(Which we can not do as well in JAVA SE).
			 */
		});
	}
}
