package com.example.hibernate.datasource;

/**
 * Enum represents different types of database products and there DataSourceConfiguration
 * 
 * @author amit
 *
 */
public enum DatabaseProduct {

	POSTGRES(PostgresDataSourceConfiguration.getInstance());

	DatabaseProduct(DataSourceConfiguration dataSourceConfiguration) {
		this.dataSourceConfiguration = dataSourceConfiguration;
	}

	private DataSourceConfiguration dataSourceConfiguration;
	
	public DataSourceConfiguration getDataSourceConfiguration() {
		return dataSourceConfiguration;
	}
}
