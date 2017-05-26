package pl.mcdev.leagueoflegends.system.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.util.UserUtils;
import pl.mcdev.leagueoflegends.system.Config;
import pl.mcdev.leagueoflegends.util.Logger;

public class MysqlManager {
	
	private static MysqlManager inst;
	
	public MysqlManager(){
		inst = this;
	}
	
	public void load(){
		Mysql db = Mysql.getInst();
		if(db.openConnection() == null){
			Logger.error("Mysql connection error! Data will not be saved!");
			return;
		}
		User.table();
		Logger.info("Loading data from Mysql...");
		ResultSet users = Mysql.getInst().executeQuery("SELECT * FROM " + Config.getInst().MYSQL_PREFIX + "users");
		try {
			while(users.next()){
				User user = User.deserialize(users);
				if(user != null) user.changed();
			}
			Logger.info("Loaded users: " + UserUtils.getUsers().size());
		} catch (Exception e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		} finally {
			db.closeConnection();
		}
	}
	
	public void save(boolean all) throws ClassNotFoundException, SQLException {
		Mysql db = Mysql.getInst();
		db.openConnection();
		Logger.info("Saving data to Mysql...");
		for(User user : UserUtils.getUsers()){
			if(!all && !user.changed()) continue;
			try {
				user.insert(db);
			} catch (Exception e){
				if(Logger.exception(e.getCause())) e.printStackTrace();
			}
		}
		Logger.info("Saved users: " + UserUtils.getUsers().size());
		db.closeConnection();
	}

	public static MysqlManager getInst(){
		if(inst != null) return inst;
		return new MysqlManager();
	}
}