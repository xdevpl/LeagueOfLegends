package pl.mcdev.leagueoflegends.basic.game;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Item {
	
	private int cost;
	private ItemStack item;
	private List<String> lore;
	private String displayName;
	private boolean disabled;
	
	public Item(int itemCost, ItemStack itemItemstack, String itemDisplayName, boolean itemDisabled, List<String> itemLore){
		this.cost = itemCost;
		this.item = itemItemstack;
		this.displayName = itemDisplayName;
		this.disabled = itemDisabled;
		this.lore = itemLore;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
