package org.demo.jdbc.connection.old;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestElephantSql {

	private static final String JDBC_URL = "jdbc:postgresql://tai.db.elephantsql.com:5432/eaphrmii" ;
	private static final String JDBC_USER = "eaphrmii";
	private static final String JDBC_PASSWORD = "xxx";

	public static void log(String s) {
		System.out.println("LOG : " + s);
	}
	
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
    		log("Get Connection...");
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                		
            Statement st = con.createStatement();
            String query = "SELECT 1";
    		log("Execute query : ");
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
            	log("Result : " + rs.getString(1));
            }
            rs.close();
            st.close();

            log("Closing Connection...");
			con.close();
			log("Connection closed.");
            
            }
        catch (java.sql.SQLException e) {
    		log("ERROR : SQLException : " + e.getMessage());
        }
    }
}
