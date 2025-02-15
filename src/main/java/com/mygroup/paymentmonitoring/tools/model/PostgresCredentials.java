package com.mygroup.paymentmonitoring.tools.model;

import java.io.Serializable;
import java.util.StringJoiner;

public class PostgresCredentials implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String host;
	private String port;
	private String database;
	private String schemas;
	
	public String getUsername() {
		return username;
	}
	public PostgresCredentials setUsername(String username) {
		this.username = username;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public PostgresCredentials setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getHost() {
		return host;
	}
	public PostgresCredentials setHost(String host) {
		this.host = host;
		return this;
	}
	public String getPort() {
		return port;
	}
	public PostgresCredentials setPort(String port) {
		this.port = port;
		return this;
	}
	public String getDatabase() {
		return database;
	}
	public PostgresCredentials setDatabase(String database) {
		this.database = database;
		return this;
	}
	public String getSchemas() {
		return schemas;
	}
	public PostgresCredentials setSchemas(String schemas) {
		this.schemas = schemas;
		return this;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String  recordFormat = "\"%s\": \"%s\"";
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(String.format(recordFormat, "username",this.getUsername()));
		joiner.add(String.format(recordFormat, "password",this.getPassword()));
		joiner.add(String.format(recordFormat, "host",this.getHost()));
		joiner.add(String.format(recordFormat, "post",this.getPort()));
		joiner.add(String.format(recordFormat, "database",this.getDatabase()));
		joiner.add(String.format(recordFormat, "schema",this.getSchemas()));
		
		return joiner.toString();
	}
	
	
	

}
