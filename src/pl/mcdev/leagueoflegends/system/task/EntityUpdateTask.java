package pl.mcdev.leagueoflegends.system.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import pl.mcdev.leagueoflegends.Main;
import pl.mcdev.leagueoflegends.basic.game.entity.LeagueEntity;

public class EntityUpdateTask implements Runnable {
	
	private static EntityUpdateTask inst;

	private BukkitTask task;

	private List<LeagueEntity> entities = new ArrayList<LeagueEntity>();
	
	public EntityUpdateTask(){
		inst = this;
	}
	
	@Override
	public void run() {
		for(LeagueEntity e : new ArrayList<LeagueEntity>(entities)){
			if(e.getHp() <= 0){
				entities.remove(e);
				continue;
			}
			e.updatePath();
		}
	}
	
	public void start(){
		task = Bukkit.getScheduler().runTaskTimer(Main.getInst(), this, 5, 5);
	}
	
	public void stop(){
		if(task == null) return;
		task.cancel();
	}
	
	public void addEntity(LeagueEntity e){
		entities.add(e);
	}
	
	public static EntityUpdateTask getInst(){
		if(inst == null) new EntityUpdateTask().start();
		return inst;
	}
}
