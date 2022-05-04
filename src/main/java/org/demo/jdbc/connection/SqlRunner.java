package org.demo.jdbc.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlRunner {

	private final ConnectionProvider connectionProvider;
	
	private long obtainConnectionDuration = 0L;
	private long closeConnectionDuration = 0L;
	
	public SqlRunner(ConnectionProvider connectionProvider) {
		super();		
		this.connectionProvider = connectionProvider;
	}

	private void log(String s) {
		System.out.println("LOG : " + s);
	}
	
	public long getObtainConnectionDuration() {
		return obtainConnectionDuration;
	}

	public long getCloseConnectionDuration() {
		return closeConnectionDuration;
	}

	public String executeSQL(String query) {
		log("executeSQL('" + query +"')...");
		
		log("Get Connection...");
		long startTime = System.currentTimeMillis();
		Connection con = connectionProvider.getConnection();
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime ;
		obtainConnectionDuration = obtainConnectionDuration + duration ;

		log("Connection ready.");
        
		try {
			String result = "";
	        Statement st = con.createStatement();
			log("Execute query : " + query );
	        ResultSet rs = st.executeQuery(query);
	        while (rs.next()) {
	        	result = rs.getString(1);
	        	log("Result : " + result);
	        }
	        rs.close();
	        st.close();

	        log("Closing Connection...");
			startTime = System.currentTimeMillis();
			con.close();
			endTime = System.currentTimeMillis();
			duration = endTime - startTime ;
			closeConnectionDuration = closeConnectionDuration + duration ;
			log("Connection closed.");
			return result;
			
		} catch (SQLException e) {
			return "ERROR : " + e.getMessage() ;
		}
	}

}
