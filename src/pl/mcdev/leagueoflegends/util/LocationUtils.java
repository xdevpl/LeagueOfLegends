package pl.mcdev.leagueoflegends.util;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class LocationUtils {
	@SuppressWarnings("deprecation")
	public static Set<Location> getYLocationsBetween(Location loc1, Location loc2, double y, ItemStack is, double newy){
		Set<Location> locs = new HashSet<Location>();
		if(!loc1.getWorld().equals(loc2.getWorld())) return locs;
		int minx = loc1.getBlockX() < loc2.getBlockX() ? loc1.getBlockX() : loc2.getBlockX();
		int maxx = loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
		int minz = loc1.getBlockZ() < loc2.getBlockZ() ? loc1.getBlockZ() : loc2.getBlockZ();
		int maxz = loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ(); 
		for(int x = minx; x <= maxx; x++){
			for(int z = minz; z <= maxz; z++){
				Location loc = new Location(loc1.getWorld(), x, y, z);
				loc.setY(newy);
				if(is == null || (is.getType() == loc.getBlock().getType() && is.getData().getData() == loc.getBlock().getData())) locs.add(loc);
			}
		}
		return locs;
	}
	
	public static boolean isBetween(Location who, Location loc1, Location loc2){
		return who.getWorld().equals(loc1.getWorld()) && who.getWorld().equals(loc2.getWorld()) && ((who.getX() < loc1.getX() && who.getX() > loc2.getX()) || (who.getX() > loc1.getX() && who.getX() < loc2.getX()))
				&& ((who.getZ() < loc1.getZ() && who.getZ() > loc2.getZ()) || (who.getZ() > loc1.getZ() && who.getZ() < loc2.getZ()));
	}
}
