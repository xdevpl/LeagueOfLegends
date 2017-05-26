/*package testes.citizens;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.google.common.collect.Maps;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.SoundEffect;
import net.minecraft.server.v1_11_R1.World;
import testes.citizens.ZombieController.EntityZombieNPC;

public class ZombieController {
	
	private final Constructor<?> constructor;
	private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = Maps.newHashMap();

	
	public ZombieController() {
		NMSImpl.registerEntityClass(EntityZombieNPC.class);
		this.constructor = getConstructor(EntityZombieNPC.class);
	}
	
	private Entity bukkitEntity;

	public void remove() {
		if (this.bukkitEntity == null) {
			return;
		}
		this.bukkitEntity.remove();
		this.bukkitEntity = null;
	}

	public void spawn(final Location at, final EntityZombieNPC npc) {
		this.bukkitEntity = this.createEntity(at, npc);
	}
	
	//====
	
	protected Entity createEntity(final Location at, final EntityZombieNPC npc) {
		final net.minecraft.server.v1_11_R1.Entity entity = this.createEntityFromClass(((CraftWorld) at.getWorld()).getHandle(), npc);
		entity.setPositionRotation(at.getX(), at.getY(), at.getZ(), at.getYaw(), at.getPitch());
		final Material beneath = at.getBlock().getRelative(BlockFace.DOWN).getType();
		if (beneath.isBlock()) {
			entity.onGround = true;
		}
		return entity.getBukkitEntity();
	}

	private net.minecraft.server.v1_11_R1.Entity createEntityFromClass(final Object... args) {
		try {
			return (net.minecraft.server.v1_11_R1.Entity) this.constructor.newInstance(args);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static Constructor<?> getConstructor(final Class<?> clazz) {
		final Constructor<?> constructor = CONSTRUCTOR_CACHE.get(clazz);
		if (constructor != null) {
			return constructor;
		}
		try {
			return clazz.getConstructor(World.class, ZombieController.class);
		} catch (Exception ex) {
			throw new IllegalStateException("unable to find an entity constructor");
		}
	}
	
	//===

	public Zombie getBukkitEntity() {
		return (Zombie) bukkitEntity;
	}

	public static class EntityZombieNPC extends EntityZombie {		
		private ZombieController controller;
		private Entity e;
		
	    public boolean spawnNPC(Location at) {
	        at = at.clone();
	        loc = at;
	        spawn(at, this);
	        final boolean couldSpawn = Util.isLoaded(at) && NMSImpl.addEntityToWorld(e, CreatureSpawnEvent.SpawnReason.CUSTOM);
	        e.teleport(at);
	        if (!couldSpawn) {
	        	//TODO: Load chunk
	        }
	        if (e instanceof LivingEntity) {
	            final LivingEntity entity = (LivingEntity) e;
	            entity.setRemoveWhenFarAway(false);
	        }
	        return true;
	    }

		public EntityZombieNPC(final World world) {
			this(world, null);
		}

		public EntityZombieNPC(final World world, final EntityZombieNPC npc) {
			super(world);
			this.e = (Entity) npc;
			if (npc != null) {
				NMSImpl.clearGoals(this.goalSelector, this.targetSelector);
			}
		}

		protected void a(final double d0, final boolean flag, final IBlockData block, final BlockPosition blockposition) {
			super.a(d0, flag, block, blockposition);
		}

		protected SoundEffect bW() {
			return NMSImpl.getSoundEffect(this.npc, super.bW(), "death-sound");
		}

		protected SoundEffect bX() {
			return NMSImpl.getSoundEffect(this.npc, super.bX(), "hurt-sound");
		}

		public void collide(final net.minecraft.server.v1_11_R1.Entity entity) {
			super.collide(entity);
		}

		public boolean d(final NBTTagCompound save) {
			return this.e == null && super.d(save);
		}

		public void e(final float f, final float f1) {
			super.e(f, f1);
		}

		public void enderTeleportTo(final double d0, final double d1, final double d2) {
			super.enderTeleportTo(d0, d1, d2);
		}

		public void f(final double x, final double y, final double z) {
			super.f(x, y, z);
		}

		public void g(final float f, final float f1) {
			super.g(f, f1);
		}

		protected SoundEffect G() {
//			return NMSImpl.getSoundEffect(e, super.G(), "ambient-sound");
			return super.G();
		}

		public CraftEntity getBukkitEntity() {
			if (this.e != null) {
				this.bukkitEntity = (CraftEntity) new CraftZombie((CraftServer) Bukkit.getServer(), (EntityZombie) this);
			}
			return super.getBukkitEntity();
		}

		public Entity getNPC() {
			return this.e;
		}

		public boolean isLeashed() {
			if (this.e == null) {
				return super.isLeashed();
			}
			if (super.isLeashed()) {
				this.unleash(true, false);
			}
			return false;
			return super.isLeashed();
		}

		protected void L() {
			super.L();
		}

		public void M() {
			super.M();
		}

		public boolean m_() {
			return super.m_();
		}
	}
}*/