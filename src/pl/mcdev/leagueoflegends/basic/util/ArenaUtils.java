package pl.mcdev.leagueoflegends.basic.util;

import org.bukkit.entity.Player;

import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.basic.game.Team;
import pl.mcdev.leagueoflegends.basic.type.Status;
import pl.mcdev.leagueoflegends.system.manager.ArenaManager;

public class ArenaUtils {

	public static boolean isInArena(Player p){
		User u = User.get(p);
		for(Arena a : ArenaManager.getInst().getArenas()){
			if((a.getStatus() == Status.COUNTING || a.getStatus() == Status.WAITING) && a.getLobby().getUsers().contains(u)) return true;
			else if(a.getRed().getUsers().contains(u) || a.getBlue().getUsers().contains(u)) return true;
		}
		return false;
	}
	
	public static Arena getArena(Player p){
		User u = User.get(p);
		for(Arena a : ArenaManager.getInst().getArenas()){
			if(a.getRed().getUsers().contains(u) || a.getBlue().getUsers().contains(u) || ((a.getStatus() == Status.COUNTING || a.getStatus() == Status.WAITING) && a.getLobby().getUsers().contains(u))) return a;
		}
		return null;
	}
	
	public static Team getTeam(Player p){
		User u = User.get(p);
		for(Arena a : ArenaManager.getInst().getArenas()){
			if(a.getRed().getUsers().contains(u)) return a.getRed();
			else if(a.getBlue().getUsers().contains(u)) return a.getBlue();
			else if((a.getStatus() == Status.COUNTING || a.getStatus() == Status.WAITING) && a.getLobby().getUsers().contains(u)) return null;
		}
		return null;
	}
}

