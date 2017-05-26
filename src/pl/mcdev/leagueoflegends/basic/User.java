package pl.mcdev.leagueoflegends.basic;

import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import pl.mcdev.leagueoflegends.basic.game.LeaguePlayer;
import pl.mcdev.leagueoflegends.basic.game.entity.LeagueEntity;
import pl.mcdev.leagueoflegends.basic.util.UserUtils;
import pl.mcdev.leagueoflegends.system.Config;
import pl.mcdev.leagueoflegends.system.data.Mysql;
import pl.mcdev.leagueoflegends.system.manager.StatsManager;
import pl.mcdev.leagueoflegends.util.Logger;
import pl.mcdev.leagueoflegends.util.reflect.Reflections;

public class User {
	
	private UUID uuid;
	private boolean changes;
	private int version;
	private boolean acceptedResourcepack;
	private int gold;
	
	private Stats stats;
	
	private LeagueEntity leagueEntity;
	private LeaguePlayer leaguePlayer;
	
	public User(Player p){
		this(p.getUniqueId(), true);
	}
	
	private User(UUID id, boolean add){
		uuid = id;
		changes = true;
		if(add) UserUtils.addUser(this);
	}
	
	private User(UUID id){
		this(id, true);
	}
	
	public boolean isOnline(){
		if(this.uuid == null) return false;
		Player player = Bukkit.getPlayer(uuid);
		return player != null && player.isOnline();
	}

	public UUID getUUID(){
		return this.uuid;
	}
	public String getName(){
		return this.uuid != null ? Bukkit.getPlayer(this.uuid).getName() : null;
	}
	
	public OfflinePlayer getOfflinePlayer(){
		return this.uuid != null ? Bukkit.getPlayer(this.uuid).getPlayer() : null;
	}
	
	public Player getPlayer(){
		return this.uuid != null ? Bukkit.getPlayer(this.uuid) : null;
		//return player;
	}
	
	public int getPing(){
		int ping = 0;
		Player p = getPlayer();
		if(p == null) return ping;
		try {
			Class<?> craftPlayer = Reflections.getBukkitClass("entity.CraftPlayer");
			Object cp = craftPlayer.cast(p);
			Object handle = craftPlayer.getMethod("getHandle").invoke(cp);
			ping = (int) handle.getClass().getField("ping").get(handle);
		} catch (Exception e) {
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return ping;
	}
	
	public int getVersion() {
		if(version == 0) return 4;
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
		changes();
	}


	public static User get(Player player){
		return get(player.getUniqueId());
	}
	
	public static User get(UUID uuid){
		for(User u : UserUtils.getUsers()) if(uuid.equals(u.getUUID())) return u;
		return new User(uuid);
	}
	
	public boolean changed(){
		boolean c = changes;
		changes = false;
		return c;
	}
	
	public void changes(){
		changes = true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o.getClass() != this.getClass()) return false;
		User u = (User) o;
		if(!u.getUUID().equals(this.uuid)) return false;
		return true;
	}

	@Override
	public String toString(){
		return this.uuid.toString();
	}
	
	public static void table(){
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists " + Config.getInst().MYSQL_PREFIX + "users(");
		sb.append("uuid varchar(100) not null,");
		sb.append("version int not null,");
		sb.append("primary key (uuid));");
		Mysql db = Mysql.getInst();
		db.openConnection();
		db.executeUpdate(sb.toString());
		db.closeConnection();
	}
	
	public static User deserialize(ResultSet rs){
		if(rs == null) return null;
		try {
			User user = User.get(UUID.fromString(rs.getString("uuid")));
			user.setVersion(rs.getInt("version"));
			return user;
		} catch (Exception e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
		return null;
	}
	
	public void insert(Mysql db){
		if(uuid == null) return;
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + Config.getInst().MYSQL_PREFIX + "users (uuid, version) VALUES (");
		sb.append("'" + uuid + "',");
		sb.append(version + "");
		sb.append(") ON DUPLICATE KEY UPDATE ");
		sb.append("uuid='" + uuid + "',");
		sb.append("version=" + version + "");
		db.executeUpdate(sb.toString());
	}
	
	//=====================================================
	
	public Stats getStats(){
		if(this.stats != null) return this.stats;
		this.stats = new Stats(this);
		StatsManager.getInst().update(this);
		this.changes();
		return this.stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public boolean isAcceptedResourcepack() {
		return acceptedResourcepack;
	}

	public void setAcceptedResourcepack(boolean acceptedResourcepack) {
		this.acceptedResourcepack = acceptedResourcepack;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	public void setLeagueEntity(LeaguePlayer en){
		this.leaguePlayer = en;
	}
	public LeaguePlayer getLeaguePlayer(){
		return this.leaguePlayer;
	}
}
