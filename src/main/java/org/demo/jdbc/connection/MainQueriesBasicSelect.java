package org.demo.jdbc.connection;

import org.demo.jdbc.connection.impl.ConnectionBuilder;

public class MainQueriesBasicSelect {

	public static void main(String[] args) {
		
		// port : 6433 = pgBouncer /  5xxx = PostgreSql without pgBouncer
		// prepared statements enabled : true/false
		ConnectionConfig connectionConfig = new ConnectionConfig(6433, true);
//		ConnectionConfig connectionConfig = new ConnectionConfig(5433, true);

		// WITHOUT POOL
		SqlRunner sqlRunner = new SqlRunner(new ConnectionBuilder(connectionConfig));
		// WITH POOL HIKARI
//		SqlRunner sqlRunner = new SqlRunner(new ConnectionPoolHikari(connectionConfig));
				
		int numberOfExecutions = 20 ;
		
		long startTime = System.currentTimeMillis();
		for ( int i = 1 ; i <= numberOfExecutions ; i++ ) {
			String query = "SELECT " + i ;
			System.out.println(" - Exec SQL : " + query);
			String r = sqlRunner.executeSQL(query);
			System.out.println("   Result : " + r);
		}

		Report.printDuration(numberOfExecutions, startTime, sqlRunner);
	}

}
