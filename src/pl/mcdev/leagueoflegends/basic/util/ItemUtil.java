package pl.mcdev.leagueoflegends.basic.util;

import java.util.ArrayList;
import java.util.List;

import pl.mcdev.leagueoflegends.basic.game.Item;

public class ItemUtil {
	
	private static List<Item> shopItems = new ArrayList<Item>();
	
	public static void addItem(Item item){
		shopItems.add(item);
	}
	public static void removeItem(Item item){
		shopItems.remove(item);
	}
	//-----> TODO luxDev to zrobi :>
	public static void getItem(Object guiItem){
		
	}
	public static List<Item> getItems(){
		return shopItems;
	}
	public static boolean isDisabled(Item item){
		if(item.isDisabled()) return true;
		return false;
	}
}
