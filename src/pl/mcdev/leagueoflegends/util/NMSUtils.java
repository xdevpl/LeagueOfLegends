package pl.mcdev.leagueoflegends.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.google.common.base.Joiner;

public class NMSUtils
{
    public static final Location LOCATION = new Location(null, 0.0, 0.0, 0.0);
    
    public static float clampYaw(float yaw) {
        while (yaw < -180.0f) {
            yaw += 360.0f;
        }
        while (yaw >= 180.0f) {
            yaw -= 360.0f;
        }
        return yaw;
    }
    
    public static Location getEyeLocation(final Entity entity) {
        return (entity instanceof LivingEntity) ? ((LivingEntity)entity).getEyeLocation() : entity.getLocation();
    }
    
    public static String getMinecraftRevision() {
        final String raw = Bukkit.getServer().getClass().getPackage().getName();
        return raw.substring(raw.lastIndexOf(46) + 2);
    }
    
    public static boolean isAlwaysFlyable(final EntityType type) {
        if (type.name().toLowerCase().contains("vex")) {
            return true;
        }
        switch (type) {
            case BAT:
            case BLAZE:
            case GHAST:
            case ENDER_DRAGON:
            case WITHER: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static boolean isLoaded(final Location location) {
        if (location.getWorld() == null) {
            return false;
        }
        final int chunkX = location.getBlockX() >> 4;
        final int chunkZ = location.getBlockZ() >> 4;
        return location.getWorld().isChunkLoaded(chunkX, chunkZ);
    }
    
    public static String listValuesPretty(final Enum<?>[] values) {
        return "<e>" + Joiner.on("<a>, <e>").join((Object[])values).toLowerCase().replace('_', ' ');
    }
    
    public static boolean locationWithinRange(final Location current, final Location target, final double range) {
        return current != null && target != null && current.getWorld() == target.getWorld() && current.distanceSquared(target) < Math.pow(range, 2.0);
    }
    
    public static EntityType matchEntityType(final String toMatch) {
        return NMSUtils.<EntityType>matchEnum(EntityType.values(), toMatch);
    }
    
    public static <T extends Enum<?>> T matchEnum(final T[] values, String toMatch) {
        toMatch = toMatch.toLowerCase().replace('-', '_').replace(' ', '_');
        for (final T check : values) {
            if (toMatch.equals(check.name().toLowerCase()) || (toMatch.equals("item") && check == EntityType.DROPPED_ITEM)) {
                return check;
            }
        }
        for (final T check : values) {
            final String name = check.name().toLowerCase();
            if (name.replace("_", "").equals(toMatch) || name.startsWith(toMatch)) {
                return check;
            }
        }
        return null;
    }
    
    public static String prettyEnum(final Enum<?> e) {
        return e.name().toLowerCase().replace('_', ' ');
    }
}
