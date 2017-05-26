package testes.entity;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;

import net.minecraft.server.v1_11_R1.AttributeRanged;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.DamageSource;
import net.minecraft.server.v1_11_R1.DataWatcher;
import net.minecraft.server.v1_11_R1.DataWatcherObject;
import net.minecraft.server.v1_11_R1.DataWatcherRegistry;
import net.minecraft.server.v1_11_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.EnumDifficulty;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.EnumMonsterType;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.GroupDataEntity;
import net.minecraft.server.v1_11_R1.IAttribute;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.Items;
import net.minecraft.server.v1_11_R1.MathHelper;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_11_R1.World;
import pl.mcdev.leagueoflegends.util.NMSImpl;
import pl.mcdev.leagueoflegends.util.NMSUtils;

public class MinionTest extends EntityZombie {
	
	public static MinionTest spawn(Location at) {
		Entity entity = new MinionTest(((CraftWorld) at.getWorld()).getHandle());
		org.bukkit.entity.Entity e = entity.getBukkitEntity();
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
		return (MinionTest) entity;
	}
	
	protected static final IAttribute a = new AttributeRanged(null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).a("Spawn Reinforcements Chance");
	private static final DataWatcherObject<Boolean> bw = DataWatcher.a(MinionTest.class, DataWatcherRegistry.h);
	private static final DataWatcherObject<Integer> bx = DataWatcher.a(MinionTest.class, DataWatcherRegistry.b);
	private static final DataWatcherObject<Boolean> by = DataWatcher.a(MinionTest.class, DataWatcherRegistry.h);
	
	public MinionTest(org.bukkit.World world) {
		this(((CraftWorld) world).getHandle());
	}
	
	public MinionTest(World world) {
		super(world);
//		setSize(0.6F, 1.95F);
	}

	@Override
	protected void r() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
//		this.goalSelector.a(2, new PathfinderGoalZombieAttack(this, 1.0D, false));
//		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
//		this.goalSelector.a(7, new PathfinderGoalRandomStrollLand(this, 1.0D));
//		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
//		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		
//		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, MinionTest.class, true));
		
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
	}
	
	@Override
	protected void dk() {
	}

	//c = generic.knockbackResistance
	//f = generic.attackSpeed
	//g = generic.armor
	//h = generic.armorToughness
	//i = generic.luck
	@Override
	protected void initAttributes() {
		super.initAttributes();
		getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(1500.0);
		getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.2300000041723251D);
		getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(3.0);
		//getAttributeInstance(GenericAttributes.g).setValue(2.0D);
	}

	@Override
	protected void i() {
		super.i();
		getDataWatcher().register(bw, Boolean.valueOf(false));
		getDataWatcher().register(bx, Integer.valueOf(0));
		getDataWatcher().register(by, Boolean.valueOf(false));
	}

	@Override
	protected int getExpValue(EntityHuman entityhuman) {
		if (isBaby()) {
			this.b_ = (int) (this.b_ * 2.5F);
		}
		return super.getExpValue(entityhuman);
	}

	@Override 
	public void n() { //tick? podpalanie od 11slonca
		super.getControllerJump().a();
		super.n();
	}
	
	/*
	 * setOnFire
	 * Gdy zostanie podpalony.
	 */
	@Override
	public void setOnFire(int i) {
		return;
	}
	/*
	 * Collide
	 * Kolizja z innymi Entiy
	 */
	@Override
	public void collide(final net.minecraft.server.v1_11_R1.Entity entity) {
		//super.collide(entity);
	}
	
	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		return super.damageEntity(damagesource, f);
	}

	@Override
	public EnumMonsterType getMonsterType() {
		return EnumMonsterType.UNDEFINED;
	}

	@Override
	protected void a(DifficultyDamageScaler difficultydamagescaler) {
		super.a(difficultydamagescaler);
		if (this.random.nextFloat() < ((this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
			int i = this.random.nextInt(3);

			if (i == 0)
				setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			else
				setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
		}
	}

	@Override
	public float getHeadHeight() {
		return 1.74F;
	}

	@Override
	protected boolean c(ItemStack itemstack) {
		return (((itemstack.getItem() == Items.EGG) && (isBaby()) && (isPassenger())) ? false : super.c(itemstack));
	}

	@Nullable
	public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity) {
		return super.prepare(difficultydamagescaler, groupdataentity);
	}

	@Override
	public double ax() {
		return -0.45D;
	}

	@Override
	public void die(DamageSource damagesource) {
		if (damagesource.getEntity() instanceof EntityPlayer) {

		}
		super.die(damagesource);
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
}