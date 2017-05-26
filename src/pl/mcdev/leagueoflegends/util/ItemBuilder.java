package pl.mcdev.leagueoflegends.util;

import java.util.List;
import java.util.Map;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
	
	private ItemStack item;
	
	public ItemBuilder(ItemBuilder ib){
		this(ib.getItem().clone());
	}
	
	public ItemBuilder(ItemStack is){
		item = is;
	}
	
	public ItemBuilder(Material m){
		this(new ItemStack(m));
	}
	
	public ItemStack getItem(){
		return item;
	}
	
	public String getName(){
		if(item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) return null;
		return item.getItemMeta().getDisplayName();
	}
	
	public ItemBuilder setName(String name){
		if(name == null || name.length() < 1) return this;
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(StringUtils.colored(name));
		item.setItemMeta(im);
		return this;
	}
	
	public List<String> getLore(){
		if(item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) return null;
		return item.getItemMeta().getLore();
	}
	
	public ItemBuilder setLore(List<String> lore){
		if(lore == null || lore.size() < 1) return this;
		ItemMeta im = item.getItemMeta();
		im.setLore(StringUtils.colored(lore));
		item.setItemMeta(im);
		return this;
	}
	
	public String getSkullOwner(){
		return ((SkullMeta) item.getItemMeta()).getOwner();
	}
	
	public ItemBuilder setSkullOwner(String s){
		SkullMeta im = (SkullMeta) item.getItemMeta();
		im.setOwner(s);
		item.setItemMeta(im);
		return this;
	}
	
	public boolean containsEnchant(Enchantment e){
		return item.containsEnchantment(e);
	}
	
	public Map<Enchantment, Integer> getEnchants(){
		return item.getEnchantments();
	}
	
	public ItemBuilder addEnchant(Enchantment ench, int lvl){
		item.addEnchantment(ench, lvl);
		return this;
	}
	
	public ItemBuilder removeEnchant(Enchantment e){
		item.removeEnchantment(e);
		return this;
	}
	
	public int getAmount(){
		return item.getAmount();
	}
	
	public ItemBuilder setAmount(int a){
		item.setAmount(a);
		return this;
	}
	
	public MaterialData getData(){
		return item.getData();
	}
	
	public ItemBuilder setData(MaterialData d){
		item.setData(d);
		return this;
	}
	
	public ItemMeta getMeta(){
		return item.getItemMeta();
	}
	
	public ItemBuilder setMeta(ItemMeta m){
		item.setItemMeta(m);
		return this;
	}
	
	public short getDurability(){
		return item.getDurability();
	}
	
	public ItemBuilder setDurability(short d){
		item.setDurability(d);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ItemBuilder setColor(DyeColor c){
		item.setDurability(c.getDyeData());
		return this;
	}
	
	public Material getType(){
		return item.getType();
	}
	
	public ItemBuilder setType(Material m){
		item.setType(m);
		return this;
	}
}
