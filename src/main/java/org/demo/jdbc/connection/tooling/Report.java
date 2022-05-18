package org.demo.jdbc.connection.tooling;

public class Report {

	public static final void printDuration(int count, long startTime, SqlRunner sqlRunner) {
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime ;
		System.out.println("---");
		System.out.println("URL : " + sqlRunner.getJdbcUrl());
		System.out.println("Connection provider   : " + sqlRunner.getConnectionProviderClass());
		System.out.println("Server Prep Statement : " + sqlRunner.isServerPreparedStatementEnabled());
		System.out.println("DURATION for " + count + " requests :");
		System.out.println(" - TOTAL : " + duration + " milliseconds");
		System.out.println(" - obtain Connection : " + sqlRunner.getObtainConnectionDuration() + " milliseconds");
		System.out.println(" - close  Connection : " + sqlRunner.getCloseConnectionDuration() + " milliseconds");
	}
}
