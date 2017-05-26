package pl.mcdev.leagueoflegends.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.mcdev.leagueoflegends.basic.game.Item;

public class PlayerItemBuyEvent extends Event implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	
	private final Player player;
	private final Item item;
	
	private boolean isCancelled;
	
	public PlayerItemBuyEvent(Player p, Item i) {
		this.player = p;
		this.item = i;
	}
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}
	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;
	}
	public HandlerList getHandlers() {
	    return handlers;
	}
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	public Player getPlayer() {
		return player;
	}
	public Item getItem() {
		return item;
	}
}
