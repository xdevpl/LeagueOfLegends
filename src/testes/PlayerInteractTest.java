package testes;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.minecraft.server.v1_11_R1.EntityInsentient;
import testes.citizens.CustomEntity;
import testes.entity.MinionTest;

public class PlayerInteractTest implements Listener {
	
	@EventHandler
	public void event(PlayerInteractEntityEvent e){
		if(e.getRightClicked() != null && e.getRightClicked().getType() != null && e.getRightClicked().getType() == EntityType.SPIDER){
			NavigationTest test = new NavigationTest((EntityInsentient) ((CraftEntity) e.getRightClicked()).getHandle(), e.getPlayer().getLocation(), 1);
			test.c();
		}
	}
	@EventHandler
	public void event2(ProjectileHitEvent e){
		if(e.getHitEntity() != null && e.getEntity().getShooter() instanceof Player){
			Player shooter = (Player) e.getEntity().getShooter();
			if(e.getHitEntity().getType() == EntityType.SPIDER){
				NavigationTest test = new NavigationTest((EntityInsentient) ((CraftEntity) e.getHitEntity()).getHandle(), shooter.getLocation(), 1);
				test.c();
			}
		}
	}
	@EventHandler
	public void event3(PlayerInteractEvent e){
		if(e.getItem() != null && e.getItem().getType() == Material.BONE){
			for(org.bukkit.entity.Entity en : e.getPlayer().getLocation().getWorld().getNearbyEntities(e.getPlayer().getLocation(), 1000, 200, 1000)){
				if(!(en instanceof Player)) en.remove();
			}
		}
		if(e.getItem() != null && e.getItem().getType() == Material.STICK){
			//CustomZombie en = new CustomZombie(Bukkit.getWorlds().get(0));
//			Minion m = new Minion(en);
			//EntityUtils.spawnEntity(en, e.getPlayer().getLocation());
			//Bukkit.broadcastMessage("Spawned entity at " + en.locX + " " + en.locY + " " + en.locZ);
//			m.setTargetLocation(e.getPlayer().getLocation().add(10, 0, 0));
//			EntityUpdateTask.getInst().addEntity(m);
			
			//CustomEntity.spawn(e.getPlayer().getLocation());
			MinionTest.spawn(e.getPlayer().getLocation());
		}
	}
	
}