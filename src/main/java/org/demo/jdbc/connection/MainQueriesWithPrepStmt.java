package org.demo.jdbc.connection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.demo.jdbc.connection.impl.ConnectionBuilder;

public class MainQueriesWithPrepStmt {

	public static void main(String[] args) throws SQLException, IOException {
		
		// port : 6433 = pgBouncer /  5xxx = PostgreSql without pgBouncer
		// prepared statements enabled : true/false
//		ConnectionConfig connectionConfig = new ConnectionConfig(6433, true);
		ConnectionConfig connectionConfig = new ConnectionConfig(5433, true);

		// WITHOUT POOL
		SqlRunner sqlRunner = new SqlRunner(new ConnectionBuilder(connectionConfig));
		// WITH POOL HIKARI
//		SqlRunner sqlRunner = new SqlRunner(new ConnectionPoolHikari(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD));
		
		long startTime = System.currentTimeMillis();
		
		String sql = "SELECT ?";
		int n = 10 ;
		
		//--- TEST #1 : 1 connection for each prepared statement
		for ( int i = 1 ; i <= n ; i++ ) {
			System.out.println("--- executeSqlQueryWithPreparedStatement("+sql+","+i+")");
			int r = sqlRunner.executeSqlQueryWithPreparedStatement(sql, i);
			System.out.println("    r = " + r);
		}
		
		//--- TEST #2 : only 1 connection for all prepared statements
		PreparedStatement ps = sqlRunner.createPreparedStatement(sql);
		for ( int i = 1 ; i <= n ; i++ ) {
			System.out.println("--- executeQueryPreparedStatement("+sql+","+i+")");
			int r = sqlRunner.executeQueryPreparedStatement(ps, i);
			System.out.println("    r = " + r);
		}
		sqlRunner.closePreparedStatementAndConnection(ps);
		
		System.out.println("\n === END OF TEST") ;
		Report.printDuration(n, startTime, sqlRunner);
	}
}
