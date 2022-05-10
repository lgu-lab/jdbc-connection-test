package org.demo.jdbc.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.demo.jdbc.connection.impl.ConnectionBuilder;

public class Main2 {

	private static final String JDBC_DRIVER = "org.postgresql.Driver" ;
	
	// PostgreSQL (base "feu") via pgBouncer ( port 6433 )  ( 6xxx pgBouncer )
	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:6433/feu" ;
	
	// PostgreSQL (base "feu") sans pgBouncer ( port 5433 )  ( 5xxx PostgreSql without pgBouncer )
//	private static final String JDBC_URL = "jdbc:postgresql://10.134.49.136:5433/feu" ;
	
	private static final String JDBC_USER     = "feu" ;
	private static final String JDBC_PASSWORD = "feu" ;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		ConnectionProvider connectionProvider = new ConnectionBuilder(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD);

		List<Connection> connections = getConnections(connectionProvider, 40);
		
		wait("\n === Press ENTER to get more connections...") ;
		List<Connection> connections2 = getConnections(connectionProvider, 40);
		
		wait("\n === Press ENTER to close first set of connections...") ;
		closeConnections(connections);
		
		wait("\n === Press ENTER to close 2nd set of connections...") ;
		closeConnections(connections2);

		System.out.println("\n === END OF TEST") ;
	}

	public static List<Connection> getConnections(ConnectionProvider connectionProvider, int numberOfConnections) {
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
		System.out.println("URL : " + JDBC_URL);
		System.out.println("DURATION (" + numberOfConnections + " connections) :");
		System.out.println(" - TOTAL : " + duration + " milliseconds");
		return list;
	}
	
	public static void closeConnections(List<Connection> connections) throws SQLException {
		System.out.println("closeConnections( size = " + connections.size() + ")") ;
		for ( Connection c : connections ) {
			c.close();
		}
	}
	public static void wait(String msg) {
	    
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
