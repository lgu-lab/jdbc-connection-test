package org.demo.jdbc.connection;

public class Report {

	public static final void printDuration(int count, long startTime, SqlRunner sqlRunner) {
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime ;
		System.out.println("---");
		System.out.println("URL : " + sqlRunner.getJdbcUrl());
		System.out.println("DURATION (" + count + " requests) :");
		System.out.println(" - TOTAL : " + duration + " milliseconds");
		System.out.println(" - obtain Connection : " + sqlRunner.getObtainConnectionDuration() + " milliseconds");
		System.out.println(" - close  Connection : " + sqlRunner.getCloseConnectionDuration() + " milliseconds");

	}
}
