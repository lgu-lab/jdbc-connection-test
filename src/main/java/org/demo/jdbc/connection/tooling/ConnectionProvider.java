package org.demo.jdbc.connection.tooling;

import java.sql.Connection;

public interface ConnectionProvider {

	Connection getConnection();
	
	String getJdbcUrl();
	
	boolean isServerPreparedStatementEnabled();

}
