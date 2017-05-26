package testes;

import org.bukkit.Location;

import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.Navigation;
import net.minecraft.server.v1_11_R1.PathEntity;
import net.minecraft.server.v1_11_R1.PathfinderGoal;


public class NavigationTest extends PathfinderGoal {

	private double speed;

	private EntityInsentient entity;

	private Location loc;

	private Navigation navigation;

	public NavigationTest(EntityInsentient entity, Location loc, double speed) {
		this.entity = entity;
		this.loc = loc;
		this.navigation = (Navigation) this.entity.getNavigation();
		this.speed = speed;
	}

	@Override
	public boolean a() { //shouldStart
		return true;
	}

	@Override
	public void c() { //onStart
		PathEntity pathEntity = this.navigation.a(loc.getX(), loc.getY(), loc.getZ());
		this.navigation.a(pathEntity, speed);
	}
}
