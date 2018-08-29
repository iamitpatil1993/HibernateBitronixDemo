/**
 * 
 */
package com.example.hibernate.datasource;

import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * Class provides common data source configuration and delegates the database specific 
 * configuration to sub-classes
 * @author amit
 *
 */
public abstract class AbstractDataSourceConfiguration implements DataSourceConfiguration {

	public static final String DATASOURCE_JNDI_BINDING_NAME = "myDataSource"; 
	protected PoolingDataSource dataSource = new PoolingDataSource();

	/*
	 * Common data source configuration
	 */
	protected void baseConfiguration() {
		dataSource.setUniqueName(DATASOURCE_JNDI_BINDING_NAME);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(5);
		dataSource.setAllowLocalTransactions(true);
	}
}
