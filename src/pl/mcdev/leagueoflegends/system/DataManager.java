package pl.mcdev.leagueoflegends.system;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import pl.mcdev.leagueoflegends.Main;
import pl.mcdev.leagueoflegends.system.data.MysqlManager;
import pl.mcdev.leagueoflegends.system.thread.ActionType;
import pl.mcdev.leagueoflegends.system.thread.AsyncThread;
import pl.mcdev.leagueoflegends.util.Logger;

public class DataManager {

	private static DataManager instance;
	private volatile BukkitTask task = null;

	public DataManager() {
		instance = this;
		Config.getInst();
		MysqlManager.getInst().load();
		Logger.info("Manager loaded.");
	}

	public void save() {
		try {
			MysqlManager.getInst().save(false);
		} catch (Exception e) {
			Logger.error("An error occurred while saving data to mysql! Caused by: Exception");
			if (Logger.exception(e.getCause()))
				e.printStackTrace();
		}
	}

	public void start() {
		if (Main.getInst().isDisabling())
			return;
		if (this.task != null)
			return;
		this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInst(), new Runnable() {
			@Override
			public void run() {
				AsyncThread.action(ActionType.SAVE_DATA);
			}
		}, Config.getInst().SAVE_INTERVAL * 60 * 20, Config.getInst().SAVE_INTERVAL * 60 * 20);
	}

	public void stop() {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}

	public static void loadDefaultFiles(String[] files) {
		for (String file : files) {
			File cfg = new File(Main.getInst().getDataFolder() + File.separator + file);
			if (!cfg.exists())
				Main.getInst().saveResource(file, true);
		}
	}

	public static DataManager getInst() {
		if (instance != null)
			return instance;
		new DataManager().start();
		return instance;
	}

	public Config getConfig() {
		return Config.getInst();
	}
}
