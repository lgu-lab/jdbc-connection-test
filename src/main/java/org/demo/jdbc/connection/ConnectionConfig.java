package org.demo.jdbc.connection;

public class ConnectionConfig {
	
	private static final String JDBC_DRIVER     = "org.postgresql.Driver" ;
	private static final String JDBC_ROOT_URL   = "jdbc:postgresql://10.134.49.136:" ;
	private static final String JDBC_DATABASE   = "feu" ;
	private static final String JDBC_USER       = "feu" ;
	private static final String JDBC_PASSWORD   = "feu" ;
	
	private final int jdbcPort ; // 5xxx PostgreSQL - 6xxx pgBouncer  
	private final String jdbcURL  ;
	private final boolean serverPreparedStatement ;

	/**
	 * Constructor
	 * @param jdbcPort
	 * @param serverPreparedStatement
	 */
	public ConnectionConfig(int jdbcPort, boolean serverPreparedStatement) {
		super();
		this.jdbcPort = jdbcPort ;
		this.serverPreparedStatement = serverPreparedStatement;
		this.jdbcURL = JDBC_ROOT_URL + jdbcPort + "/" + JDBC_DATABASE ;
	}

	public String getJdbcDriver() {
		return JDBC_DRIVER;
	}

	public String getJdbcRootUrl() {
		return JDBC_ROOT_URL;
	}

	public String getJdbcDatabase() {
		return JDBC_DATABASE;
	}

	public String getJdbcUser() {
		return JDBC_USER;
	}

	public String getJdbcPassword() {
		return JDBC_PASSWORD;
	}

	public int getJdbcPort() {
		return jdbcPort;
	}

	public String getJdbcURL() {
		return jdbcURL;
	}

	public boolean isServerPreparedStatementEnabled() {
		return serverPreparedStatement;
	}
	
}
