package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.mcdev.leagueoflegends.system.manager.GuiManager;
import pl.mcdev.leagueoflegends.util.element.BloodScreenBlur;
import pl.mcdev.leagueoflegends.util.element.Title;

public class PlayerJoinList implements Listener{
	
	@EventHandler
	public void event(PlayerJoinEvent e){
		Title title = new Title("Siema mordo", "Milego grania smierdzielu", 2, 3, 10);
		title.send(e.getPlayer());
		e.getPlayer().sendMessage("Test bloodEff: ");
		BloodScreenBlur.send(e.getPlayer(), 100);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void eventHighest(PlayerJoinEvent e){
		GuiManager.set(e);
	}

}
