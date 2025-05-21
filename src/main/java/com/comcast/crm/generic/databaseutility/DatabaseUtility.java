package com.comcast.crm.generic.databaseutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.mysql.jdbc.Driver;

public class DatabaseUtility implements Framework_Constants
{
	Connection conn;
	public void getDBConnection()
	{
		try 
		{
			Driver d = new Driver();
			DriverManager.registerDriver(d);
			DriverManager.getConnection(url,username,password);
			} catch (Exception e) {
		}
	}
	
	public void closeDBConnection() throws SQLException
	{
		try {
			conn.close();
		} catch (Exception e) {
		}
		
	}
	
	public ResultSet executeSelectQuery(String query)
	{
		ResultSet result = null;
		try {
			Statement stat = conn.createStatement();
			result=stat.executeQuery(query);			
		} catch (Exception e) {
		}
		return result;
	}
	
	public int executeNonSelectQuery(String query)
	{
		int result = 0;
		try {
			Statement stat = conn.createStatement();
			result=stat.executeUpdate(query);			
		} catch (Exception e) {
		}
		return result;
	}
	
}
