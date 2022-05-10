package org.demo.jdbc.connection.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.demo.jdbc.connection.ConnectionProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPoolHikari implements ConnectionProvider {

	private final String jdbcURL;
	private final HikariDataSource hikariDataSource ;

	public ConnectionPoolHikari(String driverClass, String jdbcURL, String jdbcUser, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		// Pool config
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClass);
		config.setJdbcUrl( jdbcURL );
        config.setUsername( jdbcUser);
        config.setPassword( jdbcPassword );
        
        config.setMinimumIdle(20);
        config.setMaximumPoolSize(30);
        
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        
        // disable "Server Prepared Statements" (for pgBouncer/transaction mode)
        config.addDataSourceProperty( "prepareThreshold" , "0" );  
        
        // Datasource
        this.hikariDataSource = new HikariDataSource( config );
	}
	
	@Override
	public String getJdbcUrl() {
		return jdbcURL;
	}

	@Override
	public Connection getConnection() {
		try {
			return hikariDataSource.getConnection();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
