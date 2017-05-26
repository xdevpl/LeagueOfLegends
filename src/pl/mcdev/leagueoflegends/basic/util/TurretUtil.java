package pl.mcdev.leagueoflegends.basic.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import pl.mcdev.leagueoflegends.basic.game.Turret;


public class TurretUtil {
	
	private static List<Turret> turrets = new CopyOnWriteArrayList<>();

	public static List<Turret> getAllTurrets() {
		return turrets;
	}
	
	public static void clearTurrets() {
		turrets.clear();
	}

	public static void addTurret(Turret turret) {
		turrets.add(turret);
	}

	public static void removeTurret(Turret turret) {
		turrets.remove(turret);
	}
	public static Turret getTurretByName(int s) {
		for (Turret turret : turrets) {
			if (turret.getTurretId() == s) {
				return turret;
			}
		}
		return null;
	}

}
