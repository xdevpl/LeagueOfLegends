package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import pl.mcdev.leagueoflegends.system.manager.QueueManager;
import pl.mcdev.leagueoflegends.util.StringUtils;

public class InventoryCloseList implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void close(InventoryCloseEvent e){
		if(e.getInventory() == null || e.getInventory().getName() == null) return;
		if(e.getInventory().getName().equals(StringUtils.colored("&c&lAreny"))) QueueManager.getInst().remove(e.getView());
	}
}
