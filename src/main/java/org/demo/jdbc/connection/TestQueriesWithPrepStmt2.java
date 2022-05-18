package org.demo.jdbc.connection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.demo.jdbc.connection.tooling.ConnectionConfig;
import org.demo.jdbc.connection.tooling.ConnectionProvider;
import org.demo.jdbc.connection.tooling.Report;
import org.demo.jdbc.connection.tooling.SqlRunner;
import org.demo.jdbc.connection.tooling.impl.ConnectionPoolHikari;

public class TestQueriesWithPrepStmt2 {

	private static int INITIAL_ID_EXPERTISE = 14579776 ;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		// port : 6433 = pgBouncer /  5xxx = PostgreSql without pgBouncer
		// prepared statements enabled : true/false
		ConnectionConfig connectionConfig = new ConnectionConfig(6433, false);
//		ConnectionConfig connectionConfig = new ConnectionConfig(5433, true);

		// WITHOUT POOL
//		ConnectionProvider connectionProvider = new ConnectionBuilder(connectionConfig);
		// WITH POOL HIKARI
		ConnectionProvider connectionProvider = new ConnectionPoolHikari(connectionConfig);
			
		String sql = "sql request here with 1 '?' for prepared statement "
				;
		int n = 100 ;
		
		//--- TEST #1 : 1 connection for each prepared statement
		test1OneConnectionForEachPS(connectionProvider, sql, n) ;

		System.out.println("----------------------------");
		
		//--- TEST #2 : only 1 connection for all prepared statements
		// test2OneConnectionForAllPS(connectionProvider, sql, n);
		
		System.out.println("\n === END OF TEST") ;
	}
	
	public static void test1OneConnectionForEachPS(ConnectionProvider connectionProvider, String sql, int n) {
		SqlRunner sqlRunner = new SqlRunner(connectionProvider);
		System.out.println("--- test1 : connection for EACH prepared statement ");
		long startTime = System.currentTimeMillis();
		
		for ( int i = 1 ; i <= n ; i++ ) {
			int idExpertise = INITIAL_ID_EXPERTISE + i ;
			Object r = sqlRunner.executeSqlQueryWithPreparedStatement(sql, idExpertise);
			System.out.println("    r = " + r);
		}
		
		Report.printDuration(n, startTime, sqlRunner);
	}

	public static void test2OneConnectionForAllPS(ConnectionProvider connectionProvider, String sql, int n) {
		SqlRunner sqlRunner = new SqlRunner(connectionProvider);
		System.out.println("--- test2 : single connection for ALL prepared statements ");
		long startTime = System.currentTimeMillis();
		
		PreparedStatement ps = sqlRunner.createPreparedStatement(sql);
		for ( int i = 1 ; i <= n ; i++ ) {
			int idExpertise = INITIAL_ID_EXPERTISE + i ;
			Object r = sqlRunner.executeQueryPreparedStatement(ps, idExpertise);
			System.out.println("    r = " + r);
		}
		sqlRunner.closePreparedStatementAndConnection(ps);
		
		Report.printDuration(n, startTime, sqlRunner);
		
	}
}
