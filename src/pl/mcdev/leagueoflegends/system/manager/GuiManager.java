package pl.mcdev.leagueoflegends.system.manager;

import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import pl.mcdev.leagueoflegends.util.ItemBuilder;

public class GuiManager {
	
	public static final ItemStack SELECT_MAP = new ItemBuilder(Material.COMPASS).setName("&7Wybierz arene &9LeagueOfLegends").setLore(Arrays.asList("&aKliknij aby wybrac mape na ktorej chcesz zagrac.")).getItem();
	public static final ItemStack STATS = new ItemBuilder(Material.SKULL_ITEM).setName("&7Twoje &3statystyki").setLore(Arrays.asList("&aKliknij aby zobaczyc swoje statystyki!")).setDurability((short) 3).getItem();
	public static final ItemStack EXIT = new ItemBuilder(Material.WOOD_DOOR).setName("&7Wyjscie do &3HUB'a").setLore(Arrays.asList("&aKliknij aby powrocic na HUB'a")).getItem();
	public static final ItemStack GRAY = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setColor(DyeColor.GRAY).getItem();

	public static void set(PlayerJoinEvent e){
		PlayerInventory inv = e.getPlayer().getInventory();
		inv.clear();
		inv.setArmorContents(new ItemStack[4]);
		for(int i = 0; i < 9; i++) inv.setItem(i, GRAY);
		inv.setItem(0, EXIT);
		inv.setItem(4, SELECT_MAP);
		inv.setItem(8, STATS);
	}
}
