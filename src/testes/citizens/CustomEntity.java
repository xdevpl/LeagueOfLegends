package testes.citizens;

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
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.SoundEffect;
import net.minecraft.server.v1_11_R1.World;
import pl.mcdev.leagueoflegends.util.NMSImpl;
import pl.mcdev.leagueoflegends.util.NMSUtils;

public class CustomEntity extends EntityZombie {

	public static void spawn(Location at) {
		net.minecraft.server.v1_11_R1.Entity entity = new CustomEntity(((CraftWorld) at.getWorld()).getHandle());
		Entity e = entity.getBukkitEntity();
		entity.setPositionRotation(at.getX(), at.getY(), at.getZ(), at.getYaw(), at.getPitch());
		Bukkit.broadcastMessage("Spawned entity at " + entity.locX + " " + entity.locY + " " + entity.locZ);
		Material beneath = at.getBlock().getRelative(BlockFace.DOWN).getType();
		if (beneath.isBlock()) entity.onGround = true;
		boolean couldSpawn = NMSUtils.isLoaded(at) && NMSImpl.addEntityToWorld(e, CreatureSpawnEvent.SpawnReason.CUSTOM);
		e.teleport(at);
		if (!couldSpawn) {
			Bukkit.broadcastMessage("could not spawn");
			// TODO: Load chunk
		}
		if (e instanceof LivingEntity) {
			final LivingEntity le = (LivingEntity) e;
			le.setRemoveWhenFarAway(false);
		}
	}

	public CustomEntity(final World world) {
		super(world);
//		bukkitEntity = this.getBukkitEntity();
//		NMSImpl.clearGoals(this.goalSelector, this.targetSelector);
	}

	protected void a(final double d0, final boolean flag, final IBlockData block, final BlockPosition blockposition) {
		super.a(d0, flag, block, blockposition);
	}

	protected SoundEffect bW() {
		return super.bW();
//		return NMSImpl.getSoundEffect(this.npc, super.bW(), "death-sound");
	}

	protected SoundEffect bX() {
		return super.bX();
//		return NMSImpl.getSoundEffect(this.npc, super.bX(), "hurt-sound");
	}

	public void collide(final net.minecraft.server.v1_11_R1.Entity entity) {
		super.collide(entity);
	}

	public boolean d(final NBTTagCompound save) {
		return this.bukkitEntity == null && super.d(save);
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
		// return NMSImpl.getSoundEffect(e, super.G(), "ambient-sound");
		return super.G();
	}
	
	public void remove() {
		if (this.bukkitEntity == null) return;
		this.bukkitEntity.remove();
		this.bukkitEntity = null;
	}

	public CraftEntity getBukkitEntity() {
		if (this.bukkitEntity != null) {
			this.bukkitEntity = (CraftEntity) new CraftZombie((CraftServer) Bukkit.getServer(), (EntityZombie) this);
		}
		return super.getBukkitEntity();
	}

	public boolean isLeashed() {
		/*
		 * if (this.bukkitEntity == null) { return super.isLeashed(); } if
		 * (super.isLeashed()) { this.unleash(true, false); } return false;
		 */
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