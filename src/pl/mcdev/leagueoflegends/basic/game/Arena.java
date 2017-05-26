package pl.mcdev.leagueoflegends.basic.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.game.entity.Minion;
import pl.mcdev.leagueoflegends.basic.type.Status;
import pl.mcdev.leagueoflegends.basic.util.ArenaUtils;
import pl.mcdev.leagueoflegends.event.LeagueEntitySpawnEvent;
import pl.mcdev.leagueoflegends.system.Config;
import pl.mcdev.leagueoflegends.system.task.CountTask;
import pl.mcdev.leagueoflegends.system.task.EntityUpdateTask;
import pl.mcdev.leagueoflegends.system.task.MinionSpawnTask;
import pl.mcdev.leagueoflegends.util.StringUtils;
import pl.mcdev.leagueoflegends.util.Utils;
import testes.entity.MinionTest;

public class Arena {
	
	private final String id;
	private String name;
	
	private Status status = Status.WAITING;
	
	private Map map;
	private Lobby lobby;
	private Set set;
	private Team red;
	private Team blue;
	
	private Path path;
	
	private int max;
	
	public Arena(String n){
		id = n;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Arena setName(String name) {
		this.name = StringUtils.colored(name);
		return this;
	}

	public Team getRed() {
		return red;
	}

	public Arena setRed(Team red) {
		this.red = red;
		return this;
	}

	public Team getBlue() {
		return blue;
	}

	public Arena setBlue(Team blue) {
		this.blue = blue;
		return this;
	}

	public Status getStatus() {
		return status;
	}

	public Arena setStatus(Status status) {
		this.status = status;
		return this;
	}

	public int getMax() {
		return max;
	}

	public Arena setMax(int max) {
		this.max = max;
		return this;
	}

	public Lobby getLobby() {
		return lobby;
	}

	public Arena setLobby(Lobby lobby) {
		this.lobby = lobby;
		return this;
	}

	public Set getSet() {
		return set;
	}

	public Arena setSet(Set set) {
		this.set = set;
		return this;
	}

	public Map getMap() {
		return map;
	}

	public Arena setMap(Map map) {
		this.map = map;
		return this;
	}
	
	public Path getPath() {
		return path;
	}

	public Arena setPath(Path path) {
		this.path = path;
		return this;
	}
	
	public Arena createPath(){
		path = new Path(this);
		return this;
	}

	public void start(){
		status = Status.INGAME;
		checkTeams();
		lobby.getUsers().clear();
		for(User u : red.getUsers()) {
			u.getStats().addPlayed();
			Player p = u.getPlayer();
			p.teleport(red.getSpawn());
			set.give(p);
		}
		for(User u : blue.getUsers()) {
			u.getStats().addPlayed();
			Player p = u.getPlayer();
			p.teleport(blue.getSpawn());
			set.give(p);
		}
		spawnTask();
	}
	
	private void checkTeams(){
		List<User> l = lobby.getUsers();
		List<User> f = new ArrayList<User>();
		for(User u : l) if(!red.getUsers().contains(u) && !blue.getUsers().contains(u)) f.add(u);
		for(User u : f) {
			if(blue.getUsers().size() < red.getUsers().size()) blue.getUsers().add(u);
			else red.getUsers().add(u);
			Utils.sendMsg(u.getPlayer(), "&7&lPrzydzielono cie do druzyny " + (blue.getUsers().contains(u) ? blue.getName() : red.getName()) + "&7&l.");
		}
	}
	
	public void checkEnd(){
		
	}
	
	public void quit(User u){
		Team t = ArenaUtils.getTeam(u.getPlayer());
		if(t != null) {
			t.removeUser(u);
			t.addLeft(u);
		}
		u.getPlayer().teleport(Config.getInst().SPAWN);
	}

	public void end(){
		for(User u : blue.getUsers()){
			if(blue.isWin()) u.getStats().addWin();
			else u.getStats().addLose();
			u.getPlayer().teleport(Config.getInst().SPAWN);
		}
		for(User u : red.getUsers()){
			if(red.isWin()) u.getStats().addWin();
			else u.getStats().addLose();
			u.getPlayer().teleport(Config.getInst().SPAWN);
		}
		for(User u : blue.getLeft()){
			if(blue.isWin()) u.getStats().addWin();
			else u.getStats().addLose();
		}
		for(User u : red.getLeft()){
			if(red.isWin()) u.getStats().addWin();
			else u.getStats().addLose();
		}
		blue.reset();
		red.reset();
		map.regenerate();//TODO potem bedzie try/catch blok
	}
	
	public void join(Player p) {
		switch(status){
		case ERROR:
			Utils.sendMsg(p, "&cNa arenie wystepuja bledy, zglos to administracji.");
			return;
		case DISABLED:
			Utils.sendMsg(p, "&cArena jest nieaktywna.");
			return;
		case INGAME:
			Utils.sendMsg(p, "&cGra na arenie juz trwa.");
			return;
		case RESTARTING:
			Utils.sendMsg(p, "&cArena sie restartuje.");
			return;
		case WAITING:
		case COUNTING:
		default:
		}
		if(ArenaUtils.isInArena(p)){
			Utils.sendMsg(p, "&cJestes juz na arenie. Opusc ja, aby dolaczyc do innej.");
			return;
		}
		if(lobby.getUsers().size() >= max){
			Utils.sendMsg(p, "&cArena jest pelna.");
			return;
		}
		p.closeInventory();
		Utils.sendMsg(p, "&a&lDolaczyles do areny " + name + "&a! &8&l(&7&l" + (lobby.getUsers().size() + 1) + "&8&l/&7&l" + max + "&8&l)");
		for(User u : lobby.getUsers()) Utils.sendMsg(u.getPlayer(), "&7&l" + p.getName() + " &a&ldolaczyl do areny! &8&l(&7&l" + (lobby.getUsers().size() + 1) + "&8&l/&7&l" + max + "&8&l)");
		lobby.addUser(User.get(p));
		p.teleport(this.lobby.getSpawn());
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		if(lobby.getUsers().size() >= max/2) count();
	}
	
	private void count(){
		new CountTask(this);
		status = Status.COUNTING;
	}
	private void spawnTask(){
		new MinionSpawnTask(this);
	}
	public void spawnMinions(){
		for(int i =0; i<3; i++){
		MinionTest e = MinionTest.spawn(blue.getMidMinionSpawn());
		e.setCustomNameVisible(true);
		e.setCustomName(StringUtils.colored("&d&lOpluty mikrofon oskara"));
		Minion m = new Minion(e, this, blue);
		LeagueEntitySpawnEvent ev = new LeagueEntitySpawnEvent(m);
		Bukkit.getPluginManager().callEvent(ev);
		m.setTargetLocation(red.getMidMinionSpawn());
		EntityUpdateTask.getInst().addEntity(m);
	}}
	public List<User> getPlayers(){
		List<User> list = new ArrayList<User>();
		for(User u : blue.getUsers()) list.add(u);
		for(User u : red.getUsers()) list.add(u);
		return list;
	}
}
