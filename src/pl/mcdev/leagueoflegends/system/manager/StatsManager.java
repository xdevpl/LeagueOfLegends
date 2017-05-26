package pl.mcdev.leagueoflegends.system.manager;

import java.util.ArrayList;
import java.util.Collections;

import pl.mcdev.leagueoflegends.basic.Stats;
import pl.mcdev.leagueoflegends.basic.User;

public class StatsManager {
	
	private static StatsManager inst;
	private final ArrayList<Stats> users = new ArrayList<Stats>();
	
	public StatsManager() {
		inst = this;
	}
	
	public void update(User user) {
		if(!this.users.contains(user.getStats())) this.users.add(user.getStats());
		Collections.sort(this.users);
	}
	
	public void remove(User user) {
		this.users.remove(user.getStats());
		Collections.sort(this.users);
	}
	
	public int getPosition(User user) {
		return this.users.indexOf(user.getStats()) + 1;
	}
	
	public User getUser(int i) {
		if (i - 1 < this.users.size()) return (this.users.get(i - 1)).getUser();
		return null;
	}
	
	public int users() {
		return this.users.size();
	}
	
	public static StatsManager getInst() {
		if (inst == null) new StatsManager();
		return inst;
	}
}