package pl.mcdev.leagueoflegends.basic.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import pl.mcdev.leagueoflegends.basic.User;

public class Lobby {
	
	private final String id;
	private String name;
	private Location spawn;
	
	private List<User> users = new ArrayList<User>();
	
	public Lobby(String n){
		id = n;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Lobby setName(String name) {
		this.name = name;
		return this;
	}

	public Location getSpawn() {
		return spawn;
	}

	public Lobby setSpawn(Location spawn) {
		this.spawn = spawn;
		return this;
	}

	public List<User> getUsers() {
		return users;
	}

	public Lobby setUsers(List<User> users) {
		this.users = users;
		return this;
	}
	
	public Lobby addUser(User user) {
		users.add(user);
		return this;
	}
	
	public Lobby removeUser(User user) {
		if(users.contains(user)) users.remove(user);
		return this;
	}

}
