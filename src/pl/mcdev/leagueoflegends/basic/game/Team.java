package pl.mcdev.leagueoflegends.basic.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.type.TeamType;

public class Team {
	
	private final String id;
	private String name;
	private TeamType type;
	
	private Set set;
	
	private boolean win = false;
	
	private List<User> users = new ArrayList<User>();
	private List<User> left = new ArrayList<User>();
	
	private Location spawn;
	private Location topMinionSpawn;
	private Location botMinionSpawn;
	private Location midMinionSpawn;
	
	private Nexus nexus;
	
	public Team(String n){
		id = n;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Team setName(String name) {
		this.name = name;
		return this;
	}

	public List<User> getUsers() {
		return users;
	}

	public Team setUsers(List<User> users) {
		this.users = users;
		return this;
	}
	
	public Team addUser(User user) {
		users.add(user);
		return this;
	}
	
	public Team removeUser(User user) {
		if(users.contains(user)) users.remove(user);
		return this;
	}
	
	public List<User> getLeft() {
		return left;
	}

	public Team setLeft(List<User> users) {
		this.left = users;
		return this;
	}
	
	public Team addLeft(User user) {
		left.add(user);
		return this;
	}
	
	public Team removeLeft(User user) {
		if(left.contains(user)) left.remove(user);
		return this;
	}

	public Location getSpawn() {
		return spawn;
	}

	public Team setSpawn(Location spawn) {
		this.spawn = spawn;
		return this;
	}

	public Set getSet() {
		return set;
	}

	public Team setSet(Set set) {
		this.set = set;
		return this;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o.getClass() != this.getClass()) return false;
		if(o instanceof Team) return false;
		Team t = (Team) o;
		if(!id.equals(t.getId()) || !name.equals(t.getName()) || !set.equals(t.getSet()) || !spawn.equals(t.getSpawn()) || !users.equals(t.getUsers())) return false;
		return true;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
	
	public void reset() {
		this.left = new ArrayList<User>();
		this.users = new ArrayList<User>();
		win = false;
	}
	
	public Location getTopMinionSpawn() {
		return topMinionSpawn;
	}

	public Team setTopMinionSpawn(Location l) {
		topMinionSpawn = l;
		return this;
	}

	public Location getBotMinionSpawn() {
		return botMinionSpawn;
	}

	public Team setBotMinionSpawn(Location l) {
		botMinionSpawn = l;
		return this;
	}

	public Location getMidMinionSpawn() {
		return midMinionSpawn;
	}

	public Team setMidMinionSpawn(Location l) {
		midMinionSpawn = l;
		return this;
	}

	public Nexus getNexus() {
		return nexus;
	}

	public Team setNexus(Nexus nexus) {
		this.nexus = nexus;
		return this;
	}

	public TeamType getType() {
		return type;
	}

	public Team setType(TeamType type) {
		this.type = type;
		return this;
	}
}

