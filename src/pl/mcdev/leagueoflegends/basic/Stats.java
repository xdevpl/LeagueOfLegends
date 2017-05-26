package pl.mcdev.leagueoflegends.basic;

public class Stats implements Comparable<Stats> {

	private final String name;
	private final User user;
	
	private int played;
	private int win;
	private int lose;
	
	public Stats(User u){
		user = u;
		name = u.getName();
	}

	public String getName() {
		return name;
	}

	public User getUser() {
		return user;
	}

	public int getWin() {
		return win;
	}
	
	public void addWin() {
		this.win += 1;
		user.changes();
	}
	
	public void removeWin(int win) {
		this.win -= win;
		user.changes();
	}

	public void setWin(int win) {
		this.win = win;
		user.changes();
	}

	public int getLose() {
		return lose;
	}
	
	public void addLose() {
		this.lose += 1;
		user.changes();
	}
	
	public void removeLose(int lose) {
		this.lose -= lose;
		user.changes();
	}

	public void setLose(int lose) {
		this.lose = lose;
		user.changes();
	}
	
	public int getPlayed() {
		return played;
	}
	
	public void addPlayed() {
		this.played += 1;
		user.changes();
	}

	public void removePlayed(int played) {
		this.played -= played;
		user.changes();
	}

	public void setPlayed(int played) {
		this.played = played;
		user.changes();
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o.getClass() != this.getClass()) return false;
		Stats s = (Stats) o;
		if(!s.getName().equalsIgnoreCase(getName())) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return Integer.toString(getWin());
	}
	
	@Override
	public int compareTo(Stats s) {
		int i = Integer.compare(s.getWin(), getWin());
		if(i == 0){
			if(getName() == null) return -1;
			if(s.getName() == null) return 1;
			i = getName().compareTo(s.getName());
		}
		return i;
	}

}
