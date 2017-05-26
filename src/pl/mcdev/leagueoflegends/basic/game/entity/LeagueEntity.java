package pl.mcdev.leagueoflegends.basic.game.entity;

import pl.mcdev.leagueoflegends.system.LeaguePathfinder;

public interface LeagueEntity {
	
	public void updatePath();
	
	public int getHp();
	
	public net.minecraft.server.v1_11_R1.Entity getEntity();
	
	public LeaguePathfinder getPath();
	
}

/*
public abstract class LeagueEntity extends net.minecraft.server.v1_11_R1.Entity {
	
	public LeagueEntity(World world) {
		super(world);
	}

	public abstract void updatePath();
	
	public abstract int getHp();
	
	public abstract Entity getEntity();
	
	public abstract LeaguePathfinder getPath();
	
}
*/