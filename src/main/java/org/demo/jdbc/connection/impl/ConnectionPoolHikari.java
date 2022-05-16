package org.demo.jdbc.connection.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.demo.jdbc.connection.ConnectionConfig;
import org.demo.jdbc.connection.ConnectionProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPoolHikari implements ConnectionProvider {

	private final String jdbcURL;
	private final HikariDataSource hikariDataSource ;
	private final boolean serverPreparedStatement ;
	
	/**
	 * Constructor
	 * @param connectionConfig
	 */
	public ConnectionPoolHikari(ConnectionConfig connectionConfig) {
		this.jdbcURL = connectionConfig.getJdbcURL();
		this.serverPreparedStatement = connectionConfig.isServerPreparedStatementEnabled();

		// Pool config
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(connectionConfig.getJdbcDriver());
		hikariConfig.setJdbcUrl( connectionConfig.getJdbcURL() );
        hikariConfig.setUsername( connectionConfig.getJdbcUser());
        hikariConfig.setPassword( connectionConfig.getJdbcPassword() );
        
        hikariConfig.setMinimumIdle(20);
        hikariConfig.setMaximumPoolSize(30);
        
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , "true" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        
		if ( this.serverPreparedStatement == false ) {
	        // disable "Server Prepared Statements" (for pgBouncer/transaction mode)
			hikariConfig.addDataSourceProperty( "prepareThreshold" , "0" );
		}
        
        // Datasource
        this.hikariDataSource = new HikariDataSource( hikariConfig );
	}
	
	@Override
	public String getJdbcUrl() {
		return jdbcURL;
	}
	
	@Override
	public boolean isServerPreparedStatementEnabled() {
		return serverPreparedStatement;
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
