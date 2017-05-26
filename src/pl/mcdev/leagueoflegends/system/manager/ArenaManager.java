package pl.mcdev.leagueoflegends.system.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.basic.game.Lobby;
import pl.mcdev.leagueoflegends.basic.game.Map;
import pl.mcdev.leagueoflegends.basic.game.Nexus;
import pl.mcdev.leagueoflegends.basic.game.Set;
import pl.mcdev.leagueoflegends.basic.game.Team;
import pl.mcdev.leagueoflegends.basic.type.TeamType;

public class ArenaManager {
	
	private static ArenaManager inst;

	private List<Arena> arenas = new ArrayList<Arena>(); 
	
	public ArenaManager(){
		inst = this;
		createArena();
	}
	
	public Arena getArenaByName(String n){
		for(Arena a : arenas) if(a.getName().equals(n)) return a;
		return null;
	}
	
	public List<Arena> getArenas(){
		return arenas;
	}

	public static ArenaManager getInst() {
		return inst;
	}
	
	public void createArena(){
		arenas.add(new Arena("lol1")
				.setName("&2&lArena &8&l[&a&l1&8&l]")
				.setMax(2)
				.setBlue(new Team("blue1")
						.setName("&9Blue")
						.setType(TeamType.BLUE)
						.setSpawn(new Location(Bukkit.getWorld("world"), -171, 53, 172))
						.setTopMinionSpawn(new Location(Bukkit.getWorlds().get(0), -150, 52, 125))
						.setMidMinionSpawn(new Location(Bukkit.getWorlds().get(0), -130, 52, 131))
						.setBotMinionSpawn(new Location(Bukkit.getWorlds().get(0), -124, 52, 151))
						.setNexus(new Nexus(new Location(Bukkit.getWorld("world"), -151, 52, 151))))
				.setRed(new Team("red1")
						.setName("&cRed")
						.setType(TeamType.RED)
						.setSpawn(new Location(Bukkit.getWorld("world"), 171, 53, -171))
						.setTopMinionSpawn(new Location(Bukkit.getWorlds().get(0), 150, 52, -123))
						.setMidMinionSpawn(new Location(Bukkit.getWorlds().get(0), 133, 52, -133))
						.setBotMinionSpawn(new Location(Bukkit.getWorlds().get(0), 124, 52, -150))
						.setNexus(new Nexus(new Location(Bukkit.getWorld("world"), 151, 52, -151))))
				.setLobby(new Lobby("lobby1")
						.setName("&aLobby")
						.setSpawn(new Location(Bukkit.getWorld("world"), 0, 200, 0)))
				.setSet(new Set("set1")
						.setName("&cSet")
						.addArmor(new ItemStack(Material.DIAMOND_HELMET))
						.addInventory(new ItemStack(Material.DIAMOND_SWORD)))
				.setMap(new Map("summonersrift")
						.setName("&eSummoners Rift"))
				.createPath());
	}
}