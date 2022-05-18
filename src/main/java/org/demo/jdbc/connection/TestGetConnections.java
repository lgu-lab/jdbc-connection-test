package org.demo.jdbc.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.demo.jdbc.connection.tooling.ConnectionConfig;
import org.demo.jdbc.connection.tooling.ConnectionProvider;
import org.demo.jdbc.connection.tooling.impl.ConnectionBuilder;

public class TestGetConnections {

	public static void main(String[] args) throws SQLException, IOException {
		
		// port : 6433 = pgBouncer /  5xxx = PostgreSql without pgBouncer
		// prepared statements enabled : true/false
		ConnectionConfig connectionConfig = new ConnectionConfig(5433, true);

		// WITHOUT POOL
		ConnectionProvider connectionProvider = new ConnectionBuilder(connectionConfig);
		
		List<Connection> connections = getConnections(connectionProvider, 100);
		
		wait("\n === Press ENTER to get more connections...") ;
		List<Connection> connections2 = getConnections(connectionProvider, 100);
		
		wait("\n === Press ENTER to close first set of connections...") ;
		closeConnections(connections);
		
		wait("\n === Press ENTER to close 2nd set of connections...") ;
		closeConnections(connections2);

//		test2(connectionProvider);
		
		System.out.println("\n === END OF TEST") ;
	}

	private static void test2(ConnectionProvider connectionProvider) throws SQLException {
		List<Connection> connections = new LinkedList<>();
		try {
			connections = getConnections(connectionProvider, 20);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wait("\n === Press ENTER to close first set of connections...") ;
			closeConnections(connections);
		}
	}
	
	private static List<Connection> getConnections(ConnectionProvider connectionProvider, int numberOfConnections) {
		System.out.println("getConnections(" + numberOfConnections + ")") ;
		long startTime = System.currentTimeMillis();
		List<Connection> list = new LinkedList<>();
		for ( int i = 1 ; i <= numberOfConnections ; i++ ) {
			System.out.println("get connection " + i) ;
			list.add( connectionProvider.getConnection() );
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime ;

		System.out.println("---");
		System.out.println("URL : " + connectionProvider.getJdbcUrl());
		System.out.println("DURATION (" + numberOfConnections + " connections) :");
		System.out.println(" - TOTAL : " + duration + " milliseconds");
		return list;
		
	}
	
	private static void closeConnections(List<Connection> connections) throws SQLException {
		System.out.println("closeConnections( size = " + connections.size() + ")") ;
		for ( Connection c : connections ) {
			c.close();
		}
	}
	
	private static void wait(String msg) {
	    
	    System.out.println(msg);
	    try {
	    	int c = System.in.read();
			while (  c != '\n' ) {
				c = System.in.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
