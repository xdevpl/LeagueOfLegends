package pl.mcdev.leagueoflegends.basic.game;

import org.bukkit.Location;

import net.minecraft.server.v1_11_R1.Entity;
import pl.mcdev.leagueoflegends.basic.type.TeamType;
import pl.mcdev.leagueoflegends.util.StringUtils;
import pl.mcdev.leagueoflegends.util.element.Hologram;

public class Turret {
	
	private Location centerLocation;
	private Location hologramLocation;
	private int turretId;
	private int turretDamage;
	private int turretHp;
	private boolean destroyed;
	private TeamType team;
	private Entity entity;
	private Hologram hologram;
	private Arena turretArena;
	
	public Turret(Location centerTurretLocation){
		this.centerLocation = centerTurretLocation;
		this.hologramLocation = centerTurretLocation.subtract(2,0,0);
		this.hologram = new Hologram("turret"+this.turretId);
		this.updateHologram();
	}

	public Location getCenterLocation() {
		return centerLocation;
	}

	public void setCenterLocation(Location centerLocation) {
		this.centerLocation = centerLocation;
	}

	public int getTurretId() {
		return turretId;
	}

	public void setTurretId(int turretId) {
		this.turretId = turretId;
	}

	public int getTurretDamage() {
		return turretDamage;
	}

	public void setTurretDamage(int turretDamage) {
		this.turretDamage = turretDamage;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public TeamType getTeam() {
		return team;
	}

	public void setTeam(TeamType team) {
		this.team = team;
	}

	public int getTurretHp() {
		return turretHp;
	}

	public void setTurretHp(int turretHp) {
		this.turretHp = turretHp;
	}

	public Arena getTurretArena() {
		return turretArena;
	}

	public void setTurretArena(Arena turretArena) {
		this.turretArena = turretArena;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public void updateHologram(){
		if(this.destroyed) hologram.delete();
		hologram.change(new String[] { StringUtils.colored("&6HP &a"+this.turretHp + "/1000"), "&6Sila: &a" + this.turretDamage, "&6Druzyna: &a" + this.team});
		hologram.show(this.hologramLocation);
	}
}
