package org.demo.jdbc.connection.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.demo.jdbc.connection.ConnectionProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPoolHikari implements ConnectionProvider {

	private final HikariDataSource hikariDataSource ;

	public ConnectionPoolHikari(String driverClass, String jdbcURL, String jdbcUser, String jdbcPassword) {
		// Config
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClass);
		config.setJdbcUrl( jdbcURL );
        config.setUsername( jdbcUser);
        config.setPassword( jdbcPassword );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        // Datasource
        this.hikariDataSource = new HikariDataSource( config );
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
