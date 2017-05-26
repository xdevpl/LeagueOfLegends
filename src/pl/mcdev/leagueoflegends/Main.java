package pl.mcdev.leagueoflegends;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.mcdev.leagueoflegends.listener.AsyncPlayerChatList;
import pl.mcdev.leagueoflegends.listener.EntitySpawnList;
import pl.mcdev.leagueoflegends.listener.EntityTurretAttackList;
import pl.mcdev.leagueoflegends.listener.InventoryClickList;
import pl.mcdev.leagueoflegends.listener.InventoryCloseList;
import pl.mcdev.leagueoflegends.listener.PlayerInteractList;
import pl.mcdev.leagueoflegends.listener.PlayerJoinList;
import pl.mcdev.leagueoflegends.listener.PlayerResourcePackStatusList;
import pl.mcdev.leagueoflegends.system.DataManager;
import pl.mcdev.leagueoflegends.system.manager.ArenaManager;
import pl.mcdev.leagueoflegends.system.manager.QueueManager;
import pl.mcdev.leagueoflegends.system.task.EntityUpdateTask;
import pl.mcdev.leagueoflegends.system.thread.AsyncThread;
import pl.mcdev.leagueoflegends.util.NMSImpl;
import testes.PlayerInteractTest;
import testes.citizens.CustomEntity;
import testes.entity.MinionTest;

public class Main extends JavaPlugin {

	private static Main inst;
	private static Thread thread;
	
	private boolean disabling = false;
	
	static {
//		NMSImpl.registerEntityClass(CustomEntity.class);
		NMSImpl.registerEntityClass(MinionTest.class);
	}
	
	public void onLoad(){
		inst = this;
		thread = Thread.currentThread();
	}
	
	@Override
	public void onEnable(){
//		CustomEntityRegistry.registerCustomEntity(130, "Minion", MinionTest.class);
		//CustomEntityRegistry2.registerEntity(54, "Minion", MinionTest.class);
//		CustomEntityRegistry2.registerEntity(54, "Zombie", CustomZombie.class);
//		Cer.addCustomEntity(54, "CustomZombie", CustomZombie.class);
//		NMSImpl.registerEntityClass(CustomEntity.class);
		
		new AsyncThread().start();
		new DataManager().start();
		new ArenaManager();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new AsyncPlayerChatList(), this);
		pm.registerEvents(new PlayerJoinList(), this);
		pm.registerEvents(new PlayerInteractList(), this);
		pm.registerEvents(new InventoryClickList(), this);
		pm.registerEvents(new InventoryCloseList(), this);
		pm.registerEvents(new PlayerResourcePackStatusList(), this);
		pm.registerEvents(new EntityTurretAttackList(), this);
		pm.registerEvents(new EntitySpawnList(), this);
		
		pm.registerEvents(new PlayerInteractTest(), this);
		
		EntityUpdateTask.getInst().start();
		
/*		RegistryMaterials<MinecraftKey, Class<? extends Entity>> r = EntityTypes.b;
		for (Iterator i = r.iterator(); i.hasNext();) {

		}*/
		
	}
	@Override
	public void onDisable(){
		disabling = true;
		DataManager.getInst().stop();
		DataManager.getInst().save();
		QueueManager.getInst().stop();
		EntityUpdateTask.getInst().stop();
	}

	public static Main getInst() {
		return inst;
	}
	
	public boolean isDisabling(){
		return disabling;
	}
	
	public static Thread getThread(){
		return thread;
	}
}
