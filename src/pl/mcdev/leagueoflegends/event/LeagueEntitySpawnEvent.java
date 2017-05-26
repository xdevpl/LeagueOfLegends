package pl.mcdev.leagueoflegends.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.mcdev.leagueoflegends.basic.game.entity.LeagueEntity;

public class LeagueEntitySpawnEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
	private LeagueEntity entity;
	
	public LeagueEntitySpawnEvent(LeagueEntity who){
		this.entity = who;
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
	public LeagueEntity getEntity(){
		return this.entity;
	}
}
