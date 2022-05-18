package org.demo.jdbc.connection.tooling.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;

class DriverProvider {

	private final String driverClassName;
	private final Driver driver;
	
	public DriverProvider(String driverClassName) {
		super();
		if ( driverClassName == null ) {
			throw new IllegalArgumentException("driver class name is null");
		}		
		this.driverClassName = driverClassName;
		this.driver = createDriverInstance(driverClassName);
	}

	public String getDriverClassName() {
		return this.driverClassName;
	}
	
	public Driver getDriver() {
		if ( this.driver != null ) {
			return this.driver;
		}
		else {
			throw new IllegalStateException("Driver not initialized");
		}
	}
	
	private Driver createDriverInstance(String driverClassName) {

		// --- Try to load the driver class with the specific class loader
		Class<?> driverClass = null;
		try {
			// Loading with default class loader ...
			driverClass = loadWithDefaultClassLoader(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot load class '" + driverClassName + "' (ClassNotFoundException)", e);
		}
		if (driverClass == null) {
			// Unexpected situation
			throw new RuntimeException("Cannot load class '" + driverClassName + "' (unknown reason)");
		}

		// --- Try to create an instance of this driver
		try {
			// return (Driver) driverClass.newInstance();
			return (Driver) driverClass.getDeclaredConstructor().newInstance();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Cannot create driver instance (NoSuchMethodException)", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot create driver instance (InvocationTargetException)", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("Cannot create driver instance (InstantiationException)", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot create driver instance (IllegalAccessException)", e);
		}
	}
	
    /**
     * Loads the driver using the current class loader
     * @param driverClassName
     * @return
     * @throws ClassNotFoundException
     */
    private Class<?> loadWithDefaultClassLoader(String driverClassName) throws ClassNotFoundException
    {
    	ClassLoader classLoader = this.getClass().getClassLoader();
		return classLoader.loadClass(driverClassName);
    }

}
