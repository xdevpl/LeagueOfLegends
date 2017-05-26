package pl.mcdev.leagueoflegends.system.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import pl.mcdev.leagueoflegends.Main;
import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.basic.type.Status;
import pl.mcdev.leagueoflegends.util.Utils;

public class CountTask implements Runnable {

	private final Arena a;
	private BukkitTask task;
	private int time = 3;
	
	public CountTask(Arena a){
		this.a = a;
		if(a.getStatus() != Status.WAITING) return;
		task = Bukkit.getScheduler().runTaskTimer(Main.getInst(), this, 20, 20);
		for(User u : a.getLobby().getUsers()) Utils.sendMsg(u.getPlayer(), "&a&lRozpoczeto odliczanie!");
	}
	
	@Override
	public void run() {
		if(--time == 0){
			a.start();
			task.cancel();
			return;
		}
		if(time > 1 && time < 6) for(User u : a.getLobby().getUsers()) Utils.sendMsg(u.getPlayer(), "&7&lGra rozpocznie sie za &c&l" + time + " &7&lsekundy.");
		else if(time == 1) for(User u : a.getLobby().getUsers()) Utils.sendMsg(u.getPlayer(), "&7&lGra rozpocznie sie za &c&l" + time + " &7&lsekunde.");
		else if(time % 10 == 0) for(User u : a.getLobby().getUsers()) Utils.sendMsg(u.getPlayer(), "&7&lGra rozpocznie sie za &c&l" + time + " &7&lsekund.");	
	}
}
