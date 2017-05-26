package pl.mcdev.leagueoflegends.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import pl.mcdev.leagueoflegends.basic.game.Turret;
import pl.mcdev.leagueoflegends.basic.game.entity.LeagueEntity;
import pl.mcdev.leagueoflegends.event.EntityTurretAttackEvent;
import pl.mcdev.leagueoflegends.util.element.Title;

public class EntityTurretAttackList implements Listener {
	
	@EventHandler
	public void event(EntityTurretAttackEvent e){
		LeagueEntity attacker = e.getAttacker();
		Turret turret = e.getAttackedTurret();
		if(turret.getTurretHp() <= 0){
			turret.setDestroyed(true);
			Title title = new Title("Wieza zostala zniszczona.", "Druzyna: "+turret.getTeam(), 2,3,10);
			turret.getTurretArena().getBlue().getUsers().stream().forEach(user -> title.send(user.getPlayer()));
			turret.getTurretArena().getRed().getUsers().stream().forEach(user -> title.send(user.getPlayer()));
		}
	}
}
