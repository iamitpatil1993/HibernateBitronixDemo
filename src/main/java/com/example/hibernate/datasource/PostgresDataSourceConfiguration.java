package com.example.hibernate.datasource;

import java.io.FileInputStream;

import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * Class configures the postgres specific datasource configurations
 * @author amit
 *
 */
public class PostgresDataSourceConfiguration extends AbstractDataSourceConfiguration {

	private static final String DATA_SOURCE_PROPERTIES_FILE = "datasource/postgres_datasource.properties";
	private static final String XA_DATASOURCE_IMPL_CLASS = "org.postgresql.xa.PGXADataSource";
	
	public static PostgresDataSourceConfiguration getInstance() {
		return new PostgresDataSourceConfiguration();
	}
	
	@Override
	public PoolingDataSource configure(String connUrl) {
		if (connUrl != null) {
			throw new IllegalArgumentException("PostgreSQL XADataSource doesn't support connection URLs");
		} else {
			try {
				super.baseConfiguration();
				dataSource.setClassName(XA_DATASOURCE_IMPL_CLASS);
				String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				dataSource.getDriverProperties().load(new FileInputStream(rootPath + DATA_SOURCE_PROPERTIES_FILE));
			} catch (Exception e) {
				e.printStackTrace();
				new RuntimeException(e);
			}
			return dataSource;
		}
	}
}
