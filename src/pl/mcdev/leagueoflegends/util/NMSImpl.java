package pl.mcdev.leagueoflegends.util;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.NavigationAbstract;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_11_R1.RegistryMaterials;
import pl.mcdev.leagueoflegends.util.reflect.ReflectionUtils;

@SuppressWarnings("unchecked")
public class NMSImpl {

	private static CustomEntityRegistry ENTITY_REGISTRY;
	public static Field GOAL_FIELD;
	public static Field NETWORK_ADDRESS;
	public static final Location PACKET_CACHE_LOCATION;

	static {
		GOAL_FIELD = ReflectionUtils.getField(PathfinderGoalSelector.class, "b");
		NETWORK_ADDRESS = ReflectionUtils.getField(NetworkManager.class, "l");
		PACKET_CACHE_LOCATION = new Location(null, 0.0, 0.0, 0.0);
		try {
			final Field field = ReflectionUtils.getField(EntityTypes.class, "b");
			final Field modifiersField = ReflectionUtils.getField(Field.class, "modifiers");
			modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
			field.set(null, ENTITY_REGISTRY = new CustomEntityRegistry((RegistryMaterials<MinecraftKey, Class<? extends Entity>>) field.get(null)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static boolean addEntityToWorld(final org.bukkit.entity.Entity entity, final SpawnReason custom) {
        return getHandle(entity).world.addEntity(getHandle(entity), custom);
    }
	
    public static void registerEntityClass(final Class<?> clazz) {
        if (ENTITY_REGISTRY == null) {
            return;
        }
        Class<?> search = clazz;
        while ((search = search.getSuperclass()) != null && net.minecraft.server.v1_11_R1.Entity.class.isAssignableFrom(search)) {
            final MinecraftKey key = ENTITY_REGISTRY.b(search);
            if (key == null) {
                continue;
            }
            final int code = ENTITY_REGISTRY.a(search);
            ENTITY_REGISTRY.put(code, key, (Class<? extends net.minecraft.server.v1_11_R1.Entity>) clazz);
            return;
        }
        throw new IllegalArgumentException("unable to find valid entity superclass for class " + clazz.toString());
    }
    
    public static void setNavigationTarget(final org.bukkit.entity.Entity handle, final org.bukkit.entity.Entity target, final float speed) {
        getNavigation(handle).a(getHandle(target), (double)speed);
    }
    
    public static void shutdown() {
        if (NMSImpl.ENTITY_REGISTRY == null) {
            return;
        }
        final Field field = ReflectionUtils.getField(EntityTypes.class, "b");
        final Field modifiersField = ReflectionUtils.getField(Field.class, "modifiers");
        try {
            modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            field.set(null, NMSImpl.ENTITY_REGISTRY.getWrapped());
        }
        catch (Exception ex) {}
    }
    
/*    public void updatePathfindingRange(final NPC npc, final float pathfindingRange) {
        final EntityLiving en = getHandle(npc.getEntity());
        if (!(en instanceof EntityInsentient)) {
            if (en instanceof EntityHumanNPC) {
                ((EntityHumanNPC)en).updatePathfindingRange(pathfindingRange);
            }
            return;
        }
        if (NMSImpl.PATHFINDING_RANGE == null) {
            return;
        }
        final EntityInsentient handle = (EntityInsentient)en;
        final NavigationAbstract navigation = handle.getNavigation();
        try {
            final AttributeInstance inst = (AttributeInstance)NMSImpl.PATHFINDING_RANGE.get(navigation);
            inst.setValue((double)pathfindingRange);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }*/
    
/*    public static void clearGoals(final PathfinderGoalSelector... goalSelectors) {
        if (NMSImpl.GOAL_FIELD == null || goalSelectors == null) {
            return;
        }
        for (final PathfinderGoalSelector selector : goalSelectors) {
            try {
                final Collection<?> list = (Collection<?>)NMSImpl.GOAL_FIELD.get(selector);
                list.clear();
            }
            catch (Exception e) {
                Messaging.logTr("citizens.nms-errors.clearing-goals", e.getLocalizedMessage());
            }
        }
    }*/
    
    public static EntityLiving getHandle(final LivingEntity entity) {
        return getHandle(entity);
    }
    
    public static net.minecraft.server.v1_11_R1.Entity getHandle(final org.bukkit.entity.Entity entity) {
        if (!(entity instanceof CraftEntity)) return null;
        return ((CraftEntity) entity).getHandle();
    }
    
    public static NavigationAbstract getNavigation(final org.bukkit.entity.Entity entity) {
        final net.minecraft.server.v1_11_R1.Entity handle = getHandle(entity);
        return (handle instanceof EntityInsentient) ? ((EntityInsentient)handle).getNavigation() : null;
    }
	
}
