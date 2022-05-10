package org.demo.jdbc.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlRunner {

	private final ConnectionProvider connectionProvider;
	
	private long obtainConnectionDuration = 0L;
	private long closeConnectionDuration  = 0L;
	
	public SqlRunner(ConnectionProvider connectionProvider) {
		super();		
		this.connectionProvider = connectionProvider;
	}

	private void log(String s) {
		System.out.println("LOG : " + s);
	}
	
	public String getJdbcUrl() {
		return connectionProvider.getJdbcUrl();
	}
	
	public long getObtainConnectionDuration() {
		return obtainConnectionDuration;
	}

	public long getCloseConnectionDuration() {
		return closeConnectionDuration;
	}
	
	public Connection getConnection() {
		log("Get Connection...");
		long startTime = System.currentTimeMillis();
		Connection connection = connectionProvider.getConnection();
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime ;
		obtainConnectionDuration = obtainConnectionDuration + duration ;
		return connection;
	}
	
	public void closeConnection(Connection connection) {
        log("Closing Connection...");
        long startTime = System.currentTimeMillis();
        try {
	        connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime ;
		closeConnectionDuration = closeConnectionDuration + duration ;
	}
	
	public String executeSQL(String query) {
		log("executeSQL('" + query +"')...");
		
		Connection connection = getConnection();
        
		try {
			String result = "";
	        Statement st = connection.createStatement();
			log("Execute query : " + query );
	        ResultSet rs = st.executeQuery(query);
	        while (rs.next()) {
	        	result = rs.getString(1);
	        	log("Result : " + result);
	        }
	        rs.close();
	        st.close();

			return result;
			
		} catch (SQLException e) {
			return "ERROR : " + e.getMessage() ;
		} finally {
			closeConnection(connection);
		}
	}

	public int executeQueryWithPreparedStatement(String sql, int value ) {
		PreparedStatement ps = createPreparedStatement(sql);
		log("Execute PreparedStatement...");
		int r = executePreparedStatement(ps, value );
		
		closePreparedStatementAndConnection(ps);
		return r;
	}

	public PreparedStatement createPreparedStatement(String sql) {
		try {
			Connection connection = getConnection();
			log("Create PreparedStatement from connection...");
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}			
	
	public void closePreparedStatementAndConnection(PreparedStatement ps) {
		try {
			Connection connection = ps.getConnection();
			log("Close PreparedStatement...");
	        ps.close();	        
			closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}			
	
	public int executePreparedStatement(PreparedStatement ps, int value ) {
		try {
			ps.getConnection().setAutoCommit(false); // Begin Transaction
			ps.setInt(1, value);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int r = rs.getInt(1);
	        rs.close();
	        ps.getConnection().commit(); // Commit Transaction
			return r;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
