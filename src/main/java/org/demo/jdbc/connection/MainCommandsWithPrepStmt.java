package org.demo.jdbc.connection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.demo.jdbc.connection.impl.ConnectionBuilder;
import org.demo.jdbc.connection.impl.ConnectionPoolHikari;

public class MainCommandsWithPrepStmt {

	public static void main(String[] args) throws SQLException, IOException {
		
		// port : 6433 = pgBouncer /  5xxx = PostgreSql without pgBouncer
		// prepared statements enabled : true/false
		ConnectionConfig connectionConfig = new ConnectionConfig(6433, false);

		// WITHOUT POOL
//		ConnectionProvider connectionProvider = new ConnectionBuilder(connectionConfig);
		// WITH POOL HIKARI
		ConnectionProvider connectionProvider = new ConnectionPoolHikari(connectionConfig);
		
		String sql = "INSERT INTO tmp (id, name) values ( ?, 'abcd' ) ";
		
		test1(connectionProvider, sql);
		System.out.println("----------------------------");
		test2(connectionProvider, sql);
		System.out.println("----------------------------");
		test3(connectionProvider, sql);
	}
	
	public static void test1(ConnectionProvider connectionProvider, String sql) {
		SqlRunner sqlRunner = new SqlRunner(connectionProvider);
		System.out.println("--- test1 : 1 connection for each prepared statement ");
		int n = 10 ;
		long startTime = System.currentTimeMillis();
		for ( int i = 1 ; i <= n ; i++ ) {
			int r = sqlRunner.executeSqlCommandWithPreparedStatement(sql, i);
			System.out.println("    r = " + r);
		}
		Report.printDuration(n, startTime, sqlRunner);
	}

	public static void test2(ConnectionProvider connectionProvider, String sql) {
		SqlRunner sqlRunner = new SqlRunner(connectionProvider);
		System.out.println("--- test2 : only 1 connection for all prepared statements ");
		int n = 10 ;
		long startTime = System.currentTimeMillis();
		
		PreparedStatement ps = sqlRunner.createPreparedStatement(sql);
		for ( int i = 1 ; i <= n ; i++ ) {
			int r = sqlRunner.executeCommandPreparedStatement(ps, i);
			System.out.println("    r = " + r);
		}
		sqlRunner.closePreparedStatementAndConnection(ps);
		
		System.out.println("\n === END OF TEST") ;
		Report.printDuration(n, startTime, sqlRunner);
	}

	public static void test3(ConnectionProvider connectionProvider, String sql) {
		SqlRunner sqlRunner = new SqlRunner(connectionProvider);
		System.out.println("--- test3 : 1 connection for each prepared statement + transaction");
		int n = 10 ;
		long startTime = System.currentTimeMillis();
		for ( int i = 1 ; i <= n ; i++ ) {
			int r = sqlRunner.executeSqlCommandWithPreparedStatementInTransaction(sql, i);
			System.out.println("    r = " + r);
		}
		Report.printDuration(n, startTime, sqlRunner);
	}
}
