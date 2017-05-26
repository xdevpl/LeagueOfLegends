package pl.mcdev.leagueoflegends.basic.game;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import pl.mcdev.leagueoflegends.util.StringUtils;
import pl.mcdev.leagueoflegends.util.element.Hologram;

public class Nexus {

	private final Location location;
	private final Location hologramLocation;
	
	private int hp = 20;
	private Hologram hologram;
	private boolean destroyed = false;
	private int nexusId;
	private Team team;
	private static final Random RAND = new Random();
	
	public Nexus(Location loc){
		this.location = loc;
		this.hologramLocation = loc.clone().add(0, 5, 0);
//		this.hologram = new Hologram("nexus" + RAND.nextInt(2));
//		this.updateHologram();
	}

	public Location getLocation() {
		return location;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	public void updateHologram(){
		if(this.destroyed) hologram.delete();
		hologram.change(new String[] { StringUtils.colored("&6HP: &a" + this.hp), "2000", "&6Druzyna: &a" + this.team});
		hologram.show(hologramLocation);
		Bukkit.broadcastMessage("Hologram should be showen ID " + hologram.getID());
	}
}
