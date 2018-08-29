package com.example.hibernate.datasource;

import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * Interface defines a contract to configure data source of database product. 
 * @author amit
 *
 */

@FunctionalInterface
public interface DataSourceConfiguration {
	
	/**
	 * Configure and return PoolingDatasource by configuration specific to database product.
	 * @param connUrl JDBC connection url.
	 * @return PoolingDatasource by configuration specific to database product.
	 */
	public PoolingDataSource configure(String connUrl);

}
