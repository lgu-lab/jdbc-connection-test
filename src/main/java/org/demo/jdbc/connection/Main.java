package org.demo.jdbc.connection;

import org.demo.jdbc.connection.impl.ConnectionBuilder;
import org.demo.jdbc.connection.impl.ConnectionPoolHikari;

public class Main {

	private static final String JDBC_DRIVER = "org.postgresql.Driver" ;
	
//	// ElephantSQL ( PostgreSQL as a Service : https://www.elephantsql.com/ )  
//	private static final String JDBC_URL = "jdbc:postgresql://tai.db.elephantsql.com:5432/eaphrmii" ;
//	private static final String JDBC_USER = "eaphrmii" ;
//	private static final String JDBC_PASSWORD = "xxx" ;
	
	// PostgreSQL (base "feu") via pgBouncer ( port 6433 )  ( 6xxx pgBouncer )
	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:6433/feu" ;
	
	// PostgreSQL (base "feu") sans pgBouncer ( port 5433 )  ( 5xxx PostgreSql without pgBouncer )
//	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:5433/feu" ;
	
	private static final String JDBC_USER     = "feu" ;
	private static final String JDBC_PASSWORD = "feu" ;
	
	public static void main(String[] args) {
		
		// WITHOUT POOL
		SqlRunner sqlRunner = new SqlRunner(new ConnectionBuilder(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD));
		
		// WITH POOL HIKARI
//		SqlRunner sqlRunner = new SqlRunner(new ConnectionPoolHikari(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD));
		
		
		int numberOfExecutions = 20 ;
		
		long startTime = System.currentTimeMillis();
		for ( int i = 1 ; i <= numberOfExecutions ; i++ ) {
			String query = "SELECT " + i ;
			System.out.println(" - Exec SQL : " + query);
			String r = sqlRunner.executeSQL(query);
			System.out.println("   Result : " + r);
		}
//		long endTime = System.currentTimeMillis();
//		long duration = endTime - startTime ;
//		System.out.println("---");
//		System.out.println("URL : " + JDBC_URL);
//		System.out.println("DURATION (" + numberOfExecutions + " requests) :");
//		System.out.println(" - TOTAL : " + duration + " milliseconds");
//		System.out.println(" - obtain Connection : " + sqlRunner.getObtainConnectionDuration() + " milliseconds");
//		System.out.println(" - close  Connection : " + sqlRunner.getCloseConnectionDuration() + " milliseconds");
		Report.printDuration(numberOfExecutions, startTime, sqlRunner);
	}

}
