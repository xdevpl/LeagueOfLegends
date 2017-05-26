package pl.mcdev.leagueoflegends.event;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.mcdev.leagueoflegends.basic.game.Turret;
import pl.mcdev.leagueoflegends.basic.game.entity.LeagueEntity;

public class EntityTurretAttackEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final LeagueEntity attacker;
	private final Turret turret;
	private final Block block;

	private boolean cancelled;
	
	public EntityTurretAttackEvent(Turret turret, LeagueEntity attacker, Block block){
		this.turret = turret;
		this.attacker = attacker;
		this.block = block;
		this.cancelled = false;
	}

	public Block getAttackedBlock(){
		return this.block;
	}

	public LeagueEntity getAttacker(){
		return this.attacker;
	}

	public Turret getAttackedTurret(){
		return this.turret;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
