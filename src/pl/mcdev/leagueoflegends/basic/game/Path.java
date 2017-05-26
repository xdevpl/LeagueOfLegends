package pl.mcdev.leagueoflegends.basic.game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import pl.mcdev.leagueoflegends.basic.type.TeamType;
import pl.mcdev.leagueoflegends.util.LocationUtils;

public class Path {
	
	public Set<Location> MID_PATH = new HashSet<Location>();
	
	private Arena arena;
	
	public Path(Arena a){
		arena = a;
		load();
	}
	
	@SuppressWarnings("deprecation")
	private void load(){
		ItemStack is = new ItemStack(Material.WOOL);
		MaterialData md = is.getData();
		md.setData(DyeColor.WHITE.getWoolData());
		is.setData(md);
		MID_PATH = LocationUtils.getYLocationsBetween(arena.getBlue().getNexus().getLocation(), arena.getRed().getNexus().getLocation(), 40, is, 52);
	}
	
	public Location getNearestPath(Location loc, TeamType d){
		Team t = d == TeamType.RED ? arena.getBlue() : arena.getRed();
		Bukkit.broadcastMessage("getNearestPath");
		for(Location l : MID_PATH){
			double dist = l.distanceSquared(loc);
			boolean b = LocationUtils.isBetween(l, loc, t.getNexus().getLocation()) && dist > 3*3 && dist < 5*5;
			Bukkit.broadcastMessage(loc + " " + d + " " + l + " " + dist + " " + b);
			if(b) return l;
		}
		return loc;
	}
}
