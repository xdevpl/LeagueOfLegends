package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import pl.mcdev.leagueoflegends.basic.game.entity.LeagueEntity;
import pl.mcdev.leagueoflegends.basic.game.entity.Minion;
import pl.mcdev.leagueoflegends.event.LeagueEntitySpawnEvent;

public class EntitySpawnList implements Listener{
	
	@EventHandler
	public void event(LeagueEntitySpawnEvent e){
		LeagueEntity en = e.getEntity();
/*		if(en instanceof Minion){
			en.getPath().setSpeed(0.6);
		}*/
	}
}
