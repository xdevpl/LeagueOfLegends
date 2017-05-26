package pl.mcdev.leagueoflegends.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.mcdev.leagueoflegends.system.Config;
import pl.mcdev.leagueoflegends.util.StringUtils;

public class Utils {
	
	public static void sendMsg(CommandSender s, String msg){
		send(s, Config.getInst().PREFIX + msg);
	}
	
	public static void sendMsg(CommandSender s, String[] msg){
		if(s instanceof Player && !((Player) s).isOnline()) return;
		for(String m : msg) s.sendMessage(StringUtils.colored(Config.getInst().PREFIX + m));
	}
	
	public static void send(CommandSender s, String msg){
		if(s instanceof Player && !((Player) s).isOnline()) return;
		s.sendMessage(StringUtils.colored(msg));
	}
	
	public static void send(CommandSender s, String[] msg){
		if(s instanceof Player && !((Player) s).isOnline()) return;
		for(String m : msg) s.sendMessage(StringUtils.colored(m));
	}
	
	public static void broadcastMsg(String msg){
		broadcast(Config.getInst().PREFIX + msg);
	}
	
	public static void broadcast(String msg){
		Bukkit.broadcastMessage(StringUtils.colored(msg));
	}
	
	public static void kickMsg(Player p, String msg){
		kick(p, Config.getInst().PREFIX + msg);
	}
	
	public static void kick(Player p, String msg){
		if(p.isOnline()) p.kickPlayer(StringUtils.colored(msg));
	}
}
