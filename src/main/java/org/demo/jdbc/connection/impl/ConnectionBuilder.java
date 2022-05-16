package org.demo.jdbc.connection.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.demo.jdbc.connection.ConnectionConfig;
import org.demo.jdbc.connection.ConnectionProvider;

public class ConnectionBuilder implements ConnectionProvider {

	private final Driver driver;
	private final String jdbcURL;
	private final Properties properties;
	private final boolean serverPreparedStatement ;

	/**
	 * Constructor
	 * @param connectionConfig
	 */
	public ConnectionBuilder(ConnectionConfig connectionConfig ) {
		DriverProvider driverProvider =  new DriverProvider(connectionConfig.getJdbcDriver()) ;
		this.driver = driverProvider.getDriver();
		this.jdbcURL = connectionConfig.getJdbcURL();
		Properties jdbcProperties = new Properties() ;
		jdbcProperties.put("user", connectionConfig.getJdbcUser());
		jdbcProperties.put("password", connectionConfig.getJdbcPassword());
		this.serverPreparedStatement = connectionConfig.isServerPreparedStatementEnabled();
		if ( connectionConfig.isServerPreparedStatementEnabled() ) {
			jdbcProperties.put("prepareThreshold", "0");
		}
		this.properties = jdbcProperties;
	}
	
//	/**
//	 * Constructor
//	 * @param driverClass
//	 * @param jdbcURL
//	 * @param jdbcUser
//	 * @param jdbcPassword
//	 */
//	public ConnectionBuilder(String driverClass, String jdbcURL, String jdbcUser, String jdbcPassword) {
//		DriverProvider driverProvider = new DriverProvider(driverClass);
//		this.driver = driverProvider.getDriver();
//		
//		this.jdbcURL = jdbcURL;
//
//		Properties jdbcProperties = new Properties() ;
//		jdbcProperties.put("user", jdbcUser);
//		jdbcProperties.put("password", jdbcPassword);
//
//		// disable "Server Prepared Statements" (for pgBouncer/transaction mode) 
//		jdbcProperties.put("prepareThreshold", "0"); 
//		
//		this.properties = jdbcProperties;
//	}
	
	public Driver getDriver() {
		return driver;
	}

	@Override
	public String getJdbcUrl() {
		return jdbcURL;
	}
	
	@Override
	public boolean isServerPreparedStatementEnabled() {
		return serverPreparedStatement;
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * Creates a new JDBC connection 
	 * @param jdbcURL
	 * @param properties
	 * @return
	 */
	private Connection createConnection(String jdbcURL, Properties properties) {

		// --- Create the JDBC connection
		Connection con = null;
		try {
			con = driver.connect(jdbcURL, properties);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot connect to database (SQLException)", e);
		} catch (Exception e) {
			throw new RuntimeException("Cannot connect to database (Exception)", e);
		}

		// --- Check connection
		if (con != null) {
			return con;
		} else {
			// Oracle driver can return null when it cannot connect
			throw new RuntimeException("Cannot connect to database. JDBC driver 'connect()' has returned null.");
		}
	}

	@Override
	public Connection getConnection() {
		return createConnection(jdbcURL, properties);
	}
}
