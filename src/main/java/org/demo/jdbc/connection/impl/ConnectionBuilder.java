package org.demo.jdbc.connection.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.demo.jdbc.connection.ConnectionProvider;

public class ConnectionBuilder implements ConnectionProvider {

	private final Driver driver;
	private final String jdbcURL;
	private final Properties properties;

	public ConnectionBuilder(String driverClass, String jdbcURL, String jdbcUser, String jdbcPassword) {
		DriverProvider driverProvider = new DriverProvider(driverClass);
		this.driver = driverProvider.getDriver();
		
		this.jdbcURL = jdbcURL;

		Properties jdbcProperties = new Properties() ;
		jdbcProperties.put("user", jdbcUser);
		jdbcProperties.put("password", jdbcPassword);
		this.properties = jdbcProperties;
	}
	
//	/**
//	 * Constructor
//	 * @param driver
//	 * @param jdbcURL
//	 * @param properties
//	 */
//	public ConnectionBuilder(Driver driver, String jdbcURL, Properties properties) {
//		super();
//		if ( driver == null ) {
//			throw new IllegalArgumentException("driver is null");
//		}
//		this.driver = driver;
//		this.jdbcURL = jdbcURL;
//		this.properties = properties;
//	}

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
