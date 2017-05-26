package pl.mcdev.leagueoflegends.util;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityCreature;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;

public class EntityUtils {

	public static void clearPath(EntityInsentient c) {
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(c.goalSelector, Sets.newLinkedHashSet());
			bField.set(c.targetSelector, Sets.newLinkedHashSet());
			cField.set(c.goalSelector, Sets.newLinkedHashSet());
			cField.set(c.targetSelector, Sets.newLinkedHashSet());
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		c.goalSelector.a(0, new PathfinderGoalFloat(c));
		//c.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction((EntityCreature) c, 1.0D)); //The goal to move 
		//c.goalSelector.a(7, new PathfinderGoalRandomStroll((EntityCreature) c, 1.0D)); //The goal to walk around
		//c.goalSelector.a(8, new PathfinderGoalLookAtPlayer(c, EntityHuman.class, 0.0F)); //The goal to look at players
		//c.goalSelector.a(8, new PathfinderGoalRandomLookaround(c)); //The goal to look around
		
		c.goalSelector.a(2, new PathfinderGoalMeleeAttack((EntityCreature) c, 1.0, true)); //This adds melee attack to the mob
		//c.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>((EntityCreature) c, EntitySheep.class, 0, true, false, null)); //This line basically sets EntitySheep as a target that the mob will try to find, you can add more if you wish
	}
	
	public static void spawnEntity(Entity e, Location loc){
		e.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(e);
	}
}
