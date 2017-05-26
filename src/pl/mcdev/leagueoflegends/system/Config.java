package pl.mcdev.leagueoflegends.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import pl.mcdev.leagueoflegends.Main;
import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.util.IOUtils;
import pl.mcdev.leagueoflegends.util.Logger;
import pl.mcdev.leagueoflegends.util.Yamler;

public class Config {
	
	private static final File CONFIG = new File(Main.getInst().getDataFolder(), "config.yml");
	
	private static Config inst;
	
	private Yamler cyml;
	
	public String PREFIX;
	
	public int RANK_START;
	public double RANK_PERCENT;
	
	public Location SPAWN;
	
	public int SAVE_INTERVAL;
	public String MYSQL_PREFIX;
	public String MYSQL_HOSTNAME;
	public int MYSQL_PORT;
	public String MYSQL_DATABASE;
	public String MYSQL_USER;
	public String MYSQL_PASSWORD;
	
	public List<Arena> ARENA_LIST = new ArrayList<Arena>();

	public Config(){
		inst = this;
		this.update();
		this.load();
	}
	
	private void update(){
		this.cyml = new Yamler(CONFIG);
		String version = cyml.getString("version");
		if(version == null || !version.equals(Main.getInst().getDescription().getVersion())){
			Logger.info("Updating config.yml...");
			CONFIG.renameTo(new File(Main.getInst().getDataFolder(), "config.old"));
			IOUtils.copy(Main.getInst().getResource("config.yml"), CONFIG);
			cyml = new Yamler(CONFIG);
			Logger.info("Successfully updated config.yml!");
		}
	}

	private void load(){
		PREFIX = cyml.getString("prefix");
		
		SAVE_INTERVAL = cyml.getInt("data.save-interval");
		MYSQL_PREFIX = cyml.getString("data.mysql.table-prefix");
		MYSQL_HOSTNAME = cyml.getString("data.mysql.hostname");
		MYSQL_PORT = cyml.getInt("data.mysql.port");
		MYSQL_DATABASE = cyml.getString("data.mysql.database");
		MYSQL_USER = cyml.getString("data.mysql.user");
		MYSQL_PASSWORD = cyml.getString("data.mysql.password");
		
		Arena a = new Arena("1");
		
	}
	
	public static Config getInst(){
		if(inst != null) return inst;
		return new Config();
	}
}
