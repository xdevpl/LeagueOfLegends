package pl.mcdev.leagueoflegends.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.system.manager.ArenaManager;
import pl.mcdev.leagueoflegends.util.StringUtils;

public class InventoryClickList implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void invClick(InventoryClickEvent e) {
		if (e.isCancelled()) return;
		String n = e.getInventory().getName();
		if (n == null) return;
		ItemStack is = e.getCurrentItem();
		if (n.equals(StringUtils.colored("&c&lAreny"))){
			e.setCancelled(true);
			if(is == null || is.getItemMeta() == null || is.getItemMeta().getDisplayName() == null || is.getItemMeta().getDisplayName().equals(" ")) return;
			Arena a = ArenaManager.getInst().getArenaByName(is.getItemMeta().getDisplayName());
			if(a != null) a.join((Player) e.getWhoClicked());
			return;
		}
	}
}
