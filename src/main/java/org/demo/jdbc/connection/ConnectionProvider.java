package org.demo.jdbc.connection;

import java.sql.Connection;

public interface ConnectionProvider {

	Connection getConnection();
}
