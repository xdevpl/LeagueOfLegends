package pl.mcdev.leagueoflegends.system;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.Navigation;
import net.minecraft.server.v1_11_R1.PathEntity;
import net.minecraft.server.v1_11_R1.PathfinderGoal;
import pl.mcdev.leagueoflegends.util.EntityUtils;

public class LeaguePathfinder extends PathfinderGoal {

	private double speed = 1;
	private EntityInsentient entity;
	private Location loc;
	private Navigation navigation;
	private PathEntity pathEntity;

	public LeaguePathfinder(EntityInsentient e) {
		entity = e;
		EntityUtils.clearPath(entity);
		loc = new Location(Bukkit.getWorlds().get(0), e.locX, e.locY, e.locZ);
		navigation = (Navigation) this.entity.getNavigation();
		pathEntity = navigation.a(loc.getX(), loc.getY(), loc.getZ());
	}
	@Override
	public boolean a() {
		return true;
	}

	@Override
	public void c() {
		//navigation.a(pathEntity, speed);
		run();
	}
	
	public LeaguePathfinder setLocation(Location l){
		loc = l;
		return this;
	}
	
	public LeaguePathfinder setSpeed(double s){
		speed = s;
		return this;
	}
	
	public void run(){
		if(loc == null || speed == 0) return;
		pathEntity = navigation.a(loc.getX(), loc.getY(), loc.getZ());
		navigation.a(pathEntity, speed);
	}

	
	public Location getLocation(){
		return loc;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public LeaguePathfinder setTarget(Entity e){
		entity.setGoalTarget((EntityLiving) ((CraftEntity) e).getHandle());
		return this;
	}
}