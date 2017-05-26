package pl.mcdev.leagueoflegends.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;

public class StringUtils {

	private static final LinkedHashMap<Integer, String> values;

	static {
		(values = new LinkedHashMap<Integer, String>(6)).put(Integer.valueOf(31104000), "y");
		values.put(Integer.valueOf(2592000), "msc");
		values.put(Integer.valueOf(86400), "d");
		values.put(Integer.valueOf(3600), "h");
		values.put(Integer.valueOf(60), "min");
		values.put(Integer.valueOf(1), "s");
	}

	public static String replace(String text, String searchString, String replacement) {
		if (text == null || text.isEmpty() || searchString.isEmpty()) return text;
		if (replacement == null) replacement = "";
		int start = 0;
		int max = -1;
		int end = text.indexOf(searchString, start);
		if (end == -1) return text;
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuilder sb = new StringBuilder(text.length() + increase);
		while (end != -1) {
			sb.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) break;
			end = text.indexOf(searchString, start);
		}
		sb.append(text.substring(start));
		return sb.toString();
	}

	public static String colored(String msg) {
		if (msg == null)
			return msg;
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		msg = msg.replace("%ok", "✓");
		msg = msg.replace("%nieok", "✗");
		msg = msg.replace(">>", "»");
//		msg = msg.replace("�", "").replace("�", "");
		return msg;
	}
	
	public static String uncolored(String msg) {
		if (msg == null)
			return msg;
		msg = msg.replace("§", "&");
		msg = msg.replace("✓", "%ok");
		msg = msg.replace("✗", "%nieok");
		msg = msg.replace("»", ">>");
		return msg;
	}
	
	public static List<String> colored(List<String> s) {
		if(s == null) return null;
		List<String> n = new ArrayList<String>();
		for(String str : s) n.add(colored(str));
		return n;
	}
	
	public static String[] colored(String[] s) {
		if(s == null) return null;
		String[] n = new String[s.length];
		for (int i = 0; i < s.length; i++) n[i] = colored(s[i]);
		return n;
	}
	
	public static String join(String[] s) {
		String n = "";
		for (int i = 0; i < s.length; i++) n += s[i] + " ";
		return n.length() < 1 ? "" : n.substring(0, n.length() - 1);
	}

	public static String toString(List<String> list, boolean send) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
			sb.append(',');
			if (send) sb.append(' ');
		}
		String s = sb.toString();
		if (send) if (s.length() > 2) s = s.substring(0, s.length() - 2);
		else if (s.length() > 1) s = s.substring(0, s.length() - 1);
		return s;
	}

	public static List<String> fromString(String s) {
		if (s == null || s.isEmpty()) return new ArrayList<String>();
		return Arrays.asList(s.split(","));
	}

	public static List<String> getEmptyList() {
		return new ArrayList<String>();
	}

	public static String secondsToString(int seconds) {
		String s = "";
		for (Map.Entry<Integer, String> e : values.entrySet()) {
			int iDiv = seconds / ((Integer) e.getKey()).intValue();
			if (iDiv >= 1) {
				int x = (int) Math.floor(iDiv);
				s += (x + (String) e.getValue()) + " ";
				seconds -= x * ((Integer) e.getKey()).intValue();
			}
		}
		return s.length() < 1 ? "0s" : s.substring(0, s.length() - 1);
	}
}