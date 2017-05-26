package pl.mcdev.leagueoflegends.util.element;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import pl.mcdev.leagueoflegends.util.StringUtils;
import pl.mcdev.leagueoflegends.util.reflect.PacketUtils;


public class Title {
//	private static Class<?> PacketPlayOutTitle = Reflections.getCraftClass("PacketPlayOutTitle");
//	private static Class<?> IChatBaseComponent = Reflections.getCraftClass("IChatBaseComponent");
//	private static Class<?> PacketActions = Reflections.getCraftClass("PacketPlayOutTitle$EnumTitleAction");
//	private static Class<?> ChatSerializer = Reflections.getCraftClass("ChatComponentText");
	private int fadeIn;
	private int fadeOut;
	private int time;
	private String title;
	private String subtitle;
	
	private Object packet;
	
	public Title(String title, String subtitle, int fadeIn, int fadeOut, int time){
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.time = time;
		this.title = StringUtils.colored(title);
		this.subtitle = StringUtils.colored(subtitle);
	}
/*	public void send(Player p){
		try{
			Object[] actions = PacketActions.getEnumConstants();
			packet = PacketPlayOutTitle.getConstructor(PacketActions, IChatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(actions[2], null, fadeIn * 20, time * 20, fadeOut * 20);
			PacketUtils.sendPacket(p, packet);
			Object serialized = ChatSerializer.getConstructor(String.class).newInstance(title);
			packet = PacketPlayOutTitle.getConstructor(PacketActions, IChatBaseComponent).newInstance(actions[0], serialized);
			PacketUtils.sendPacket(p, packet);
			if(subtitle != ""){
				serialized = ChatSerializer.getConstructor(String.class).newInstance(subtitle);
				packet = PacketPlayOutTitle.getConstructor(PacketActions, IChatBaseComponent).newInstance(actions[1], serialized);
				PacketUtils.sendPacket(p, packet);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void resetTitleSubTitle(Player p){
		try{
			Object[] actions = PacketActions.getEnumConstants();
			packet = PacketPlayOutTitle.getConstructor(PacketActions, IChatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(actions[2], null, null, null, null);
			PacketUtils.sendPacket(p, packet);
			packet = PacketPlayOutTitle.getConstructor(PacketActions, IChatBaseComponent).newInstance(actions[0], null);
			PacketUtils.sendPacket(p, packet);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
	public void send(Player p){
		try{
			packet = new PacketPlayOutTitle(EnumTitleAction.RESET, null, fadeIn * 20, time * 20, fadeOut * 20);
			PacketUtils.sendPacket(p, packet);
			packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"}"));
			PacketUtils.sendPacket(p, packet);
			if(subtitle != ""){
				packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"));
				PacketUtils.sendPacket(p, packet);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public void resetTitleSubTitle(Player p){
		try{
			packet = new PacketPlayOutTitle(EnumTitleAction.RESET, null, 0, 0, 0);
			PacketUtils.sendPacket(p, packet);
			packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, null);
			PacketUtils.sendPacket(p, packet);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
