package pl.mcdev.leagueoflegends.basic.game.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.basic.game.Team;
import pl.mcdev.leagueoflegends.basic.game.Turret;
import pl.mcdev.leagueoflegends.system.LeaguePathfinder;
import testes.entity.MinionTest;

public class Minion implements LeagueEntity {

	private final net.minecraft.server.v1_11_R1.Entity entity;
	private Entity targetEntity;
	private Arena arena;
	private Team team;
	
	private LeaguePathfinder path;
	private Turret targetTurret;
	private Location targetLocation;
	
	private int hp = 20;
	private int damage = 1;
	
	private int newpath = 0;

	public Minion(MinionTest e, Arena a, Team t){
		entity = e;
		arena = a;
		team = t;
		path = new LeaguePathfinder(e);
		path.setLocation(new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
		path.setSpeed(1);
	}
	
	@Override
	public void updatePath() {
		if(targetEntity != null){
			if(!path.getLocation().equals(targetEntity.getLocation())){
				path.setLocation(targetEntity.getLocation());
			}
			path.run();
			return;
		}
		if(targetTurret != null){
			if(!path.getLocation().equals(targetTurret.getCenterLocation())){
				path.setLocation(targetTurret.getCenterLocation());
			}
			path.run();
			return;
		}
		if(targetLocation != null){
			if(++newpath > 3) newpath = 0;
			if(newpath == 1){
				targetLocation = arena.getPath().getNearestPath(entity.getBukkitEntity().getLocation(), team.getType());
			}
			if(!path.getLocation().equals(targetLocation)){
				path.setLocation(targetLocation);
			}
			path.run();
			return;
		}
	}

	@Override
	public int getHp() {
		return hp;
	}
	
	public Minion setHp(int i){
		hp = i;
		return this;
	}
	
	public net.minecraft.server.v1_11_R1.Entity getEntity(){
		return entity;
	}
	
	public Turret getTargetTurret(){
		return targetTurret;
	}
	
	public Minion setTargetTurret(Turret turret){
		this.targetTurret = turret;
		return this;
	}
	
	public Entity getTargetEntity(){
		return targetEntity;
	}
	
	public Minion setTargetEntity(Entity e){
		targetEntity = e;
		return this;
	}

	public int getDamage() {
		return damage;
	}

	public Minion setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	public Location getTargetLocation() {
		return targetLocation;
	}

	public Minion setTargetLocation(Location targetLocation) {
		this.targetLocation = targetLocation;
		return this;
	}

	public LeaguePathfinder getPath() {
		return path;
	}

	public void setPath(LeaguePathfinder path) {
		this.path = path;
	}

}
