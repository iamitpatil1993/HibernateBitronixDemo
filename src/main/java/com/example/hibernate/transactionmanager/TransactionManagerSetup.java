package com.example.hibernate.transactionmanager;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import com.example.hibernate.datasource.AbstractDataSourceConfiguration;
import com.example.hibernate.datasource.DatabaseProduct;

import bitronix.tm.Configuration;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * Encapsulates DataSource, TransactionManager initialization and user transaction management.
 * @author amit
 *
 */
public class TransactionManagerSetup {

	private static final String SERVER_ID = "myServer@07";
	private static TransactionManagerSetup selfReference = null;
	private InitialContext context = null;
	private PoolingDataSource dataSource = null;

	/**
	 * Creates and returns fully initialized transaction manager.(With data source initialized) 
	 * Making this class singleton, because we want to create and initialize the data source only once in entire application,
	 * so, we are initializing the data source and transaction manager in constructor which will get invoked only once in entire application cycle.
	
	 * @param databaseProduct Type of database for which transaction manager and data source to be initialized.(Postgres/Mysql/Derby/H2)
	 * @return Fully initialized transaction manager.(With data source initialized) 
	 * @throws Exception
	 */
	public static TransactionManagerSetup getInstance(DatabaseProduct databaseProduct) throws Exception {
		if (selfReference == null) {
			synchronized (TransactionManagerSetup.class) {
				if (selfReference == null) {
					selfReference = new TransactionManagerSetup(databaseProduct);
				}
				return selfReference;
			}
		}
		return selfReference;
	}
	
	/**
	 * Private constructor to assert singleton
	 * @param databaseProduct
	 * @throws Exception
	 */
	private TransactionManagerSetup(DatabaseProduct databaseProduct) throws Exception {
		try {
			// Instantiate initialContext, this uses implementation mentioned in jndi.properties file in classPath
			context = new InitialContext();	

			// Transaction manager configuration
			Configuration configuration = TransactionManagerServices.getConfiguration();
			configuration.setServerId(SERVER_ID);
			configuration.setDisableJmx(true);
			configuration.setJournal("null");

			// Data source configuration based on Database type
			dataSource = databaseProduct.getDataSourceConfiguration().configure(null);

			// Initialize the data source, and bind data source to initial context
			dataSource.init();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Returns the UserTransaction if any associated with current thread of execution or new using JNDI lookup
	 * @return Returns the UserTransaction if any associated with current thread of execution or new
	 */
	public UserTransaction getUserTransaction() {
		try {
			return (UserTransaction) context
					.lookup("java:comp/UserTransaction");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Returns the Data source registered to JNDI
	 * @return Returns the Data source registered to JNDO
	 */
	public DataSource getDataSource() {
		try {
			return (DataSource) context.lookup(AbstractDataSourceConfiguration.DATASOURCE_JNDI_BINDING_NAME);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Closes the entire transaction manager and data source.
	 */
	public synchronized void close() {
		if (dataSource != null && !dataSource.isDisabled()) {
			dataSource.close();
			TransactionManagerServices.getTransactionManager().shutdown();
		}
	}
}
