package pl.mcdev.leagueoflegends.basic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pl.mcdev.leagueoflegends.basic.User;

public class UserUtils {
	
	private static List<User> users = new ArrayList<User>();
	
	public static boolean playedBefore(String s){
		for(User u : users) if(u.getUUID() != null && u.getUUID().equals(s)) return true;
		return false;
	}

	public static List<UUID> getUUIDs(List<User> users){
		List<UUID> list = new ArrayList<>();
		for(User u : users) list.add(u.getUUID());
		return list;
	}
	
	public static List<String> getOnlineNames(List<User> users){
		List<String> list = new ArrayList<>();
		for(User u : users){
			if(u.isOnline()) list.add("<online>" + u.getName() + "</online>");
			else list.add(u.getName());
		}
		return list;
	}
	
/*	public static List<User> getUsers(List<String> names){
		List<User> list = new ArrayList<>();
		for(String s : names) list.add(User.get(s));
		return list;
	}*/
	
	public static void addUser(User user){
		users.add(user);
	}
	
	public static void removeUser(User user){
		users.remove(user);
	}
	
	public static List<User> getUsers(){
		return new ArrayList<User>(users);
	}
	
	public static User getUser(String n){
		for(User u : users) if(u.getName().equals(n)) return u;
		return null;
	}
	
	public static User getUser(UUID n){
		for(User u : users) if(u.getUUID().equals(n)) return u;
		return null;
	}
	
	public static Player getPlayer(String n){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().equals(n)) return p;
		}
		return null;
	}
	
	public static Player getPlayer(UUID n){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getUniqueId().equals(n)) return p;
		}
		return null;
	}

}
