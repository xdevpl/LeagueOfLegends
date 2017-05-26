package pl.mcdev.leagueoflegends.system.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import pl.mcdev.leagueoflegends.Main;
import pl.mcdev.leagueoflegends.basic.game.Arena;
import pl.mcdev.leagueoflegends.basic.type.Status;
import pl.mcdev.leagueoflegends.util.ItemBuilder;
import pl.mcdev.leagueoflegends.util.StringUtils;

public class QueueManager implements Runnable {

	private static QueueManager inst;
	
	private static final ItemStack GRAY = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setColor(DyeColor.GRAY).getItem();
	private static final ItemStack RED = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setColor(DyeColor.RED).getItem();
	private static final ItemStack LIME = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setColor(DyeColor.LIME).getItem();
	private static final ItemBuilder ARENA = new ItemBuilder(Material.DIAMOND_SWORD);
	
	private BukkitTask task;
	private List<InventoryView> inventories = new ArrayList<InventoryView>();
	
	public QueueManager(){
		inst = this;
		task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInst(), this, 10, 10);
	}
	
	private InventoryView open(Player p){
		Inventory inv = Bukkit.createInventory(p, 3*9, StringUtils.colored("&c&lAreny"));
		for(int i = 0; i < 9; i+=8) inv.setItem(i, RED);
		for(int i = 18; i < 27; i+=8) inv.setItem(i, RED);
		for(int i = 11; i < 16; i+=2) inv.setItem(i, LIME);
		for(int i = 0; i < inv.getSize(); i++) inv.setItem(i, GRAY);
		return p.openInventory(inv);
	}
	
	private void update(InventoryView view){
		if(view == null) return;
		List<Arena> al = ArenaManager.getInst().getArenas();
		Arena a = al.get(0);
		view.setItem(10, ARENA.setName(a.getName()).setLore(Arrays.asList(
				"&6Tryb: &eLeagueOfLegends",
				"&6Mapa: &e" + a.getMap().getName(),
				"&6Online: &e" + (a.getLobby().getUsers().size() + a.getBlue().getUsers().size() + a.getRed().getUsers().size()) + "&7/&e" + a.getMax(),
				"&6Status: &e" + getStatus(a.getStatus()),
				"&2Kliknij, aby dolaczyc do &2&lareny&r&2!")).getItem());
		view.setItem(12, ARENA.getItem());
		view.setItem(14, ARENA.getItem());
		view.setItem(16, ARENA.getItem());
	}
	
	private String getStatus(Status s){
		switch(s){
		case COUNTING:
			return "Odliczanie";
		case DISABLED:
			return "Wylaczona";
		case ERROR:
			return "Blad";
		case INGAME:
			return "W grze";
		case RESTARTING:
			return "Restartowanie";
		}
		return "Oczekiwanie";
	}
	
	
	@Override
	public void run(){
		for(InventoryView inv : new ArrayList<InventoryView>(inventories)) update(inv);
	}
	
	public void stop(){
		if(task == null) return;
		task.cancel();
		task = null;
	}
	
	public void remove(InventoryView view){
		if(inventories.contains(view)) inventories.remove(view);
	}
	
	public void openMenu(Player p){
		InventoryView inv = open(p);
		inventories.add(inv);
		update(inv);
	}
	
	public static QueueManager getInst(){
		if(inst == null) return new QueueManager();
		return inst;
	}
}
