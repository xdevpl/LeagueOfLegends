package pl.mcdev.leagueoflegends.basic.game;

import org.bukkit.Location;
import org.bukkit.World;

import pl.mcdev.leagueoflegends.basic.Schematic;

public class Map {

	private final String id;
	private String name;

	private Location mapCenter;
	private World mapWorld;
	private Schematic mapSchematic;

	public Map(String n) {
		id = n;
	}

	public Schematic getSchematic() {
		return mapSchematic;
	}

	public void setSchematic(Schematic schematic) {
		this.mapSchematic = schematic;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Location getCenter() {
		return mapCenter;
	}

	public Map setName(String name) {
		this.name = name;
		return this;
	}

	public void setCenter(Location center) {
		this.mapCenter = center;
	}

	public World getWorld() {
		return mapWorld;
	}

	public void setWorld(World world) {
		this.mapWorld = world;
	}

	public void regenerate() {
		// SchematicUtil.pasteSchematic(mapWorld, mapCenter, mapSchematic);
	}
}
