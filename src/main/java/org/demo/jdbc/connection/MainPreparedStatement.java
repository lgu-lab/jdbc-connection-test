package org.demo.jdbc.connection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.demo.jdbc.connection.impl.ConnectionBuilder;

public class MainPreparedStatement {

	private static final String JDBC_DRIVER = "org.postgresql.Driver" ;
	
	// PostgreSQL (base "feu") via pgBouncer ( port 6433 )  ( 6xxx pgBouncer )
	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:6433/feu" ;
	
	// prepared statement threshold = 0 to disable usage of server side prepared statements
//	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:6433/feu?prepareThreshold=0" ;
	// See also config via properties in ConnectionBuilder & ConnectionPoolHikari :

	// PostgreSQL (base "feu") sans pgBouncer ( port 5433 )  ( 5xxx PostgreSql without pgBouncer )
//	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:5433/feu" ;
	private static final String JDBC_USER     = "feu" ;
	private static final String JDBC_PASSWORD = "feu" ;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		// WITHOUT POOL
		SqlRunner sqlRunner = new SqlRunner(new ConnectionBuilder(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD));
		
		// WITH POOL HIKARI
//		SqlRunner sqlRunner = new SqlRunner(new ConnectionPoolHikari(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD));
		
		
		long startTime = System.currentTimeMillis();
		
		String sql = "SELECT ?";
		int n = 10 ;
		
		//--- TEST #1 : 1 connection for each prepared statement
		for ( int i = 1 ; i <= n ; i++ ) {
			System.out.println("--- executeQueryWithPreparedStatement("+sql+","+i+")");
			int r = sqlRunner.executeQueryWithPreparedStatement(sql, i);
			System.out.println("    r = " + r);
		}
		
		//--- TEST #2 : only 1 connection for all prepared statements
		PreparedStatement ps = sqlRunner.createPreparedStatement(sql);
		for ( int i = 1 ; i <= n ; i++ ) {
			System.out.println("--- executePreparedStatement("+sql+","+i+")");
			int r = sqlRunner.executePreparedStatement(ps, i);
			System.out.println("    r = " + r);
		}
		sqlRunner.closePreparedStatementAndConnection(ps);
		
		System.out.println("\n === END OF TEST") ;
		Report.printDuration(n, startTime, sqlRunner);
	}
}
