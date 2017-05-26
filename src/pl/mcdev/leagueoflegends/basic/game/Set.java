package pl.mcdev.leagueoflegends.basic.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Set {
	
	private final String id;
	private String name;
	
	private List<ItemStack> inventory = new ArrayList<ItemStack>();
	private List<ItemStack> armor = new ArrayList<ItemStack>();
	
	public Set(String n){
		id = n;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set setName(String name) {
		this.name = name;
		return this;
	}

	public List<ItemStack> getInventory() {
		return inventory;
	}

	public Set setInventory(List<ItemStack> inventory) {
		this.inventory = inventory;
		return this;
	}
	
	public Set addInventory(ItemStack inventory) {
		this.inventory.add(inventory);
		
		return this;
	}

	public List<ItemStack> getArmor() {
		return armor;
	}

	public Set setArmor(List<ItemStack> armor) {
		this.armor = armor;
		return this;
	}
	
	public Set addArmor(ItemStack armor) {
		this.armor.add(armor);
		return this;
	}
	
	public void give(Player p) {
		//TODO
	}
}
