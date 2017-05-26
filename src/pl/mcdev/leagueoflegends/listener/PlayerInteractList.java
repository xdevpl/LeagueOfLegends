package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.mcdev.leagueoflegends.system.manager.GuiManager;
import pl.mcdev.leagueoflegends.system.manager.QueueManager;

public class PlayerInteractList implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getItem() == null) return;
		if(e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(e.getItem().equals(GuiManager.SELECT_MAP)) QueueManager.getInst().openMenu(e.getPlayer());
	}
}
