package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.util.UserUtils;
import pl.mcdev.leagueoflegends.util.element.Title;

public class PlayerResourcePackStatusList implements Listener{
	
	@EventHandler
	public void event(PlayerResourcePackStatusEvent e){
		User u = UserUtils.getUser(e.getPlayer().getName());
		if(e.getStatus() == Status.DECLINED){
			Title title = new Title("&7Wystapil blad!", "&cAby grac na tym trybie musisz zaakceptowac texturepack", 2, 4, 12);
			title.send(e.getPlayer());
			u.setAcceptedResourcepack(false);
		}
		if(e.getStatus() == Status.FAILED_DOWNLOAD){
			Title title = new Title("&7Wystapil blad!", "&cAby grac na tym trybie musisz zaakceptowac texturepack", 2, 4, 12);
			title.send(e.getPlayer());
			u.setAcceptedResourcepack(false);
		}
		if(e.getStatus() == Status.ACCEPTED || e.getStatus() == Status.SUCCESSFULLY_LOADED){
			Title title = new Title("&7Texture pack zostal poprawnie Wczytany", "&3Od teraz mozesz grac! :)", 2, 4, 12);
			title.send(e.getPlayer());
			u.setAcceptedResourcepack(true);
		}
	}
}
