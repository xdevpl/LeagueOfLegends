package pl.mcdev.leagueoflegends.system.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pl.mcdev.leagueoflegends.system.Config;
import pl.mcdev.leagueoflegends.util.Logger;

public class Mysql {
	
private static Mysql inst;
	
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;
	private Connection connection;

	public Mysql(){
		inst = this;
		Config c = Config.getInst();
		this.hostname = c.MYSQL_HOSTNAME;
		this.port = String.valueOf(c.MYSQL_PORT);
		this.database = c.MYSQL_DATABASE;
		this.user = c.MYSQL_USER;
		this.password = c.MYSQL_PASSWORD;
		this.connection = null;
		this.firstConnection();
	}

	public Connection openConnection() {
		try {
			if(checkConnection()) return connection;
			Class.forName("com.mysql.jdbc.Driver");
			String s = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
			connection = DriverManager.getConnection(s, this.user, this.password);
			return connection;
		} catch (Exception e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return null;
	}
	
	public void firstConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + this.hostname + ":" + this.port + "/?user=" + this.user + "&password=" + this.password;
			Connection conn = DriverManager.getConnection(url);
			Statement s = conn.createStatement();
			s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.database);
			conn.close();
		} catch (Exception e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
	}

	public boolean checkConnection(){
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return connection != null;
	}

	public Connection getConnection() {
		if(!checkConnection()) openConnection();
		return connection;
	}

	public boolean closeConnection() {
		if(connection == null) return false;
		try {
			connection.close();
		} catch (Exception e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return true;
	}

	public ResultSet executeQuery(String query) {
		try {
			if(!checkConnection()) openConnection();
			if(connection == null) return null;
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (Exception e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return null;
	}

	public int executeUpdate(String query){
		try {
			if(!checkConnection()) openConnection();
			if(connection == null){
				openConnection();
				if(connection == null) return 0;
			}
			Statement statement = connection.createStatement();
			if(statement == null) return 0;
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			if(e.getSQLState().equals("42S21")) return 4221;
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return 0;
	}

	public static Mysql getInst(){
		if(inst == null) return new Mysql();
		return inst;
	}
}
