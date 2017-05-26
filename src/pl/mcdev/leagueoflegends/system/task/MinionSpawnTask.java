package pl.mcdev.leagueoflegends.system.task;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;

import pl.mcdev.leagueoflegends.Main;
import pl.mcdev.leagueoflegends.basic.User;
import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.basic.type.Status;
import pl.mcdev.leagueoflegends.util.element.Title;

public class MinionSpawnTask implements Runnable{

	private final Arena arena;
	private BukkitTask task;
	private boolean firstWave;
	private int time = 40;
	private List<String> firstWaveMess = Arrays.asList("&7Tworzenie minionow.","&3Wszystkie miniony sie stworza za 30 sekund.");
	private List<String> firstWaveMessDone = Arrays.asList("&7Tworzenie minionow.","&3Miniony sie stworzyly!");
	
	public MinionSpawnTask(Arena a){
		arena = a;
		if(a.getStatus() != Status.INGAME) return;
		task = Bukkit.getScheduler().runTaskTimer(Main.getInst(), this, 20, 20);
	}
	@Override
	public void run() {
		time--;
		if(arena.getStatus() != Status.INGAME) task.cancel();
		if(time == 30 && !firstWave){
			Title title = new Title(firstWaveMess.get(0), firstWaveMess.get(1), 2,2,20*2);
			for(User u : arena.getPlayers()){
				title.send(u.getPlayer());
				u.getPlayer().playSound(u.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 1f);
			}
		}
		if(time == 0 && !firstWave) {
			firstWave=true;
			Title title = new Title(firstWaveMessDone.get(0), firstWaveMessDone.get(1), 2,2,20*2);
			for(User u : arena.getPlayers()){
				title.send(u.getPlayer());
				u.getPlayer().playSound(u.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 1f);
			}
			arena.spawnMinions();
		}else if(time == 0 && firstWave) arena.spawnMinions();
	}
}
