package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatList implements Listener{
	
	@EventHandler
	public void listener(AsyncPlayerChatEvent e){
/*		String mess = e.getMessage();
		if(e.getMessage().startsWith("arena")){
			Arena arena = ArenaUtil.get(mess.split(":")[0]);
		}*/
	}
}
