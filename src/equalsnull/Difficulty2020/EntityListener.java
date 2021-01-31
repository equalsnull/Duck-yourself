package equalsnull.Difficulty2020;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityListener implements Listener{
	private Main  plugin;
	private List<EntityType> notTargeting =Arrays.asList(EntityType.PHANTOM);
	public EntityListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		for(World world:Bukkit.getWorlds()) {
			for(Entity e:world.getEntities()) {
				handleEntity(e);
			}
		}
	}
	@EventHandler
	public void onSpawn(EntitySpawnEvent event) {
		handleEntity(event.getEntity());
		
	}
	public void onCreeperignite(ExplosionPrimeEvent event) {
		((Creeper)event.getEntity()).explode();
	}
	public void handleEntity(Entity e) {
		EntityType et = e.getType();
		if(e instanceof Player) {
			((Player) e).setNoDamageTicks(0);
		}
		if(e instanceof Monster && !notTargeting.contains(et)) {
			if(Math.random() > 0.98 && e.getWorld().getName().equalsIgnoreCase("world")) {
				e.getWorld().spawnEntity(e.getLocation(), EntityType.PHANTOM);
				e.remove();
				return ;
			}
			try {
				new setTarget(((Mob) e),32).runTaskTimer(plugin, 0, 5);
			((Monster) e).setTarget(Utils.nearestPlayer(e.getLocation(),32));
			}catch(NullPointerException | ClassCastException exception) {
				
			}
		}
		if(et == EntityType.SKELETON) {
			LivingEntity entity = (LivingEntity)e;
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 15);
			entity.getEquipment().setItemInMainHand(bow);
			entity.getEquipment().setItemInMainHandDropChance(0);
			ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
			helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack leggins = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
			boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			entity.getEquipment().setItemInMainHand(bow);
			entity.getEquipment().setItemInMainHandDropChance(0);
			entity.getEquipment().setHelmet(helmet);
			entity.getEquipment().setHelmetDropChance(0);
			entity.getEquipment().setChestplate(chestplate);
			entity.getEquipment().setChestplateDropChance(0);
			entity.getEquipment().setLeggings(leggins);
			entity.getEquipment().setLeggingsDropChance(0);
			entity.getEquipment().setBoots(boots);
			entity.getEquipment().setBootsDropChance(0);
			//Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Skelly((ProjectileSource) e), 0L, 10L); //0 Tick initial delay, 20 Tick (1 Second) between repeats
			//new Shooter((ProjectileSource) e).runTaskTimer(plugin, 0, 10);
		}
		if(e instanceof Creeper) {
			Creeper c = (Creeper)e;
			c.setPowered(true);
			((Creeper) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,Integer.MAX_VALUE,1,true,false));
			new explodeWhennearby(c).runTaskTimer(plugin, 0, 1);
		}
		if(e instanceof Zombie) {
			LivingEntity entity = (LivingEntity)e;
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
			ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
			helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack leggins = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
			boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			entity.getEquipment().setItemInMainHand(sword);
			entity.getEquipment().setItemInMainHandDropChance(0);
			entity.getEquipment().setHelmet(helmet);
			entity.getEquipment().setHelmetDropChance(0);
			entity.getEquipment().setChestplate(chestplate);
			entity.getEquipment().setChestplateDropChance(0);
			entity.getEquipment().setLeggings(leggins);
			entity.getEquipment().setLeggingsDropChance(0);
			entity.getEquipment().setBoots(boots);
			entity.getEquipment().setBootsDropChance(0);
			new StareToMine((Mob) e).runTaskTimer(plugin, 0, 20);
			((LivingEntity) e).getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
		}
		if(e instanceof Spider) {
			new spooderWebs((Spider)e).runTaskTimer(plugin, 0, 30);
			
		}
		if(e instanceof Phantom) {
			Phantom p = (Phantom)e;
			//p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,Integer.MAX_VALUE,1,true,false));
			p.teleport(p.getLocation().add(0,32,0));
			new phantomBomber(p).runTaskTimer(plugin, 0, 200);
		}
		if(e instanceof ProjectileSource) {
			if(et == EntityType.SKELETON)
				new Shooter((ProjectileSource) e,Arrow.class).runTaskTimer(plugin, 0, 100);
			if(e instanceof Blaze)
				new Shooter((ProjectileSource) e,SmallFireball.class).runTaskTimer(plugin, 0, 100);
			if(e instanceof Ghast)
				new Shooter((ProjectileSource) e,Fireball.class).runTaskTimer(plugin, 0, 100);
			if(e instanceof EnderDragon)
				new Shooter((ProjectileSource) e,DragonFireball.class).runTaskTimer(plugin, 0, 100);
			if(e instanceof Witch) {
				new Shooter((ProjectileSource) e,ThrownPotion.class).runTaskTimer(plugin, 0, 100);
			}
			
		}
		if(e instanceof Projectile) {
			if(((Projectile)e).getShooter() instanceof Monster)
				new Targeting((Projectile)e,((Mob) ((Projectile) e).getShooter()).getTarget()).runTaskTimer(plugin,0,5);
			if(e instanceof ThrownPotion) {
				ThrownPotion tp = (ThrownPotion)e;
				ItemStack item = new ItemStack(Material.SPLASH_POTION);
				PotionMeta meta = (PotionMeta) item.getItemMeta();
				meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 200, 1), true);
				meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1), true);
				item.setItemMeta(meta);

				ThrownPotion thrownPotion = (ThrownPotion) e;
				thrownPotion.setItem(item);
				return;
			}
			//((Projectile)e).setGravity(false);
			
		}
	}
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		
		if(event.getEntity() instanceof Player) {
			((Player) event.getEntity()).setNoDamageTicks(0);
		}
	}
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if(event.getEntityType() == EntityType.WITHER_SKELETON) {
			if(Math.random()> 0.99) {
				event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.WITHER);
			}
		}else if((event.getEntityType() == EntityType.SKELETON)||(event.getEntityType() == EntityType.ZOMBIE)) {
			if(event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
				event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.WITHER_SKELETON);
			}
		}
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {

		if(event.getEntity() instanceof Player) {
			if(event.getDamager() instanceof Spider) {
				((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON,(int) (Math.random()*29.0)+1, 1, true, false, false));
			}
			return;
		}
		if(event.getDamager() instanceof Projectile) {
			if(((Projectile) event.getDamager()).getShooter() instanceof Player) {
				return;
			}
		}
		if((event.getDamager() instanceof Player)) {
			return;
		}
		event.setCancelled(true);
	}
	@EventHandler
    public void onHit(ProjectileHitEvent event){
		((Projectile)event.getEntity()).setMetadata("Hit", new FixedMetadataValue(plugin,true));
		//event.getEntity().setVelocity(new Vector(0,0,0));
	}
	@EventHandler
	public void onPickupEvent(EntityChangeBlockEvent event) {
		if(event.getEntityType() == EntityType.ENDERMAN) {
			event.getBlock().breakNaturally();
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onTeleport(EntityTeleportEvent event) {
		if(event.getEntityType() == EntityType.ENDERMAN) {
			event.getTo().getWorld().spawnEntity(event.getTo(), EntityType.ENDERMITE);
		}
	}
	private class Targeting extends BukkitRunnable{

		public Projectile obj;
		public LivingEntity target=null;
		public Targeting(Projectile obj) {
			this.obj = obj;
		}
		public Targeting(Projectile obj, LivingEntity target) {
			this.obj = obj;
			this.target = target;
		}
		
		@Override
		public void run() {
			try {	
				if(obj.isDead()) {
					this.cancel();
				}
				if(obj.hasMetadata("Hit")) {
					this.cancel();
				}
			if(target == null) {
				target = Utils.nearestPlayer(obj.getLocation());
			}
			Vector newVector = target.getBoundingBox().getCenter()
					.subtract(obj.getLocation().toVector()).normalize();
			obj.setVelocity(newVector);/*(new Vector(
					target.getEyeLocation().getX()-obj.getLocation().getX(),
					target.getEyeLocation().getY()-obj.getLocation().getY(),
					target.getEyeLocation().getZ()-obj.getLocation().getZ()).add(new Vector(0.5,1,0.5))));//*/
			//System.out.println("Target : "+target.getName());
			}catch(NullPointerException e) {
			}
		}
		
	}
	private class Shooter extends BukkitRunnable{

		public ProjectileSource shooter;
		public Class<? extends Projectile> projectile;
		Shooter(ProjectileSource shooter,Class<? extends Projectile> projectile){
			this.shooter = shooter;
			this.projectile = projectile;
		}
		@Override
		public void run() {
			try {
				if(((Entity)shooter).isDead()){
					return;
				}
					((Mob)shooter).setTarget(Utils.nearestPlayer(((Entity)shooter).getLocation()));
					//if(((LivingEntity)shooter).hasLineOfSight(Utils.nearestPlayer(((Entity)shooter).getLocation()))) {
						shooter.launchProjectile(projectile);
					//}
			}catch(NullPointerException e) {
				this.cancel();
			}catch(ClassCastException e) {
				this.cancel();
			}
			
		}
		
	}
	private class StareToMine extends BukkitRunnable{

		public Mob entity;
		public Block last=null;
		private Vector locationprevious= null;;
		private Set<Material> transparent= new HashSet<Material>();
		StareToMine(Mob entity){
			this.entity = entity;
			transparent.add(Material.AIR);
			transparent.add(Material.WATER);
			transparent.add(Material.LAVA);
			transparent.add(Material.COBWEB);
			this.locationprevious = entity.getLocation().getBlock().getLocation().toVector();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(entity.isDead()) {
				this.cancel();
			}
			try {
			if(entity.getTarget() == null) {
				return;
			}
			//if(!this.locationprevious.equals(entity.getLocation().getBlock().getLocation().toVector()))
				//return;
			//if(last.getLocation().equals(entity.getLineOfSight(null, 1).get(1).getLocation())) {
			/*
			 if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB) {
				if(entity.getTarget().getLocation().getY() > entity.getLocation().getY()) {
					last = entity.getTargetBlock(transparent, 1);
					if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB)
						last = last.getWorld().getBlockAt(last.getLocation().add(0, 1, 0));
					if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB)
						last = last.getWorld().getBlockAt(entity.getLocation().add(0, 1, 0));
				}else if(entity.getTarget().getLocation().getY() < entity.getLocation().getY()) {
					last = last.getWorld().getBlockAt(entity.getTargetBlock(transparent, 1).getLocation().subtract(0, 1, 0));
					if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB)
						last = last.getWorld().getBlockAt(last.getLocation().subtract(0, 1, 0));
					if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB)
						last = entity.getTargetBlock(transparent, 1);
				}else if(entity.getTarget().getLocation().getY() == entity.getLocation().getY()) {
					last = entity.getTargetBlock(transparent, 1);
					if(last.isEmpty())
						last = last.getWorld().getBlockAt(last.getLocation().subtract(0, 1, 0));
				}
			}//*/
				//last = entity.getWorld().getBlockAt(entity.getLocation().add(Utils.axify(entity.getLocation().getDirection())));
				//System.out.println(last.getLocation()+" : "+Utils.axify(entity.getLocation().getDirection()));
			
			
			/*if(last == null) {
				last = entity.getTargetBlock(transparent, 1);
				if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB) {
					last = entity.getTargetBlock(transparent, 1);
					if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB)
						last = last.getWorld().getBlockAt(last.getLocation().subtract(0, 1, 0));
				}
			}else {
				Block current = entity.getTargetBlock(transparent, 1);
				if(current.isEmpty() || current.isLiquid() ||current.getType() == Material.COBWEB) {
					current = entity.getTargetBlock(transparent, 1);
					if(current.isEmpty() || current.isLiquid() ||current.getType() == Material.COBWEB)
						current = current.getWorld().getBlockAt(last.getLocation().subtract(0, 1, 0));
				}
				if(last.equals(current)) {
					 last.breakNaturally();
					 last = null;
				}
			}
			*/
			last = entity.getTargetBlock(transparent, 1);
			if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB) {
				last = entity.getTargetBlock(transparent, 1);
				if(last.isEmpty() || last.isLiquid() ||last.getType() == Material.COBWEB)
					last = last.getWorld().getBlockAt(last.getLocation().subtract(0, 1, 0));
			}
			if(!last.isEmpty() && !last.isLiquid() && last.getType() != Material.COBWEB) {
				if(last.getBlockData().getMaterial().getHardness() == -1) {
					return;
				}
				if(!last.hasMetadata("Damage")) {
					last.setMetadata("Damage", new FixedMetadataValue(plugin,0.1f));
				}else {
					float temp = last.getMetadata("Damage").get(0).asFloat();
					last.removeMetadata("Damage", plugin);
					last.setMetadata("Damage", new FixedMetadataValue(plugin,temp+0.1f));
					if(last.getBlockData().getMaterial().getHardness() >= temp+0.1f) {
						last.breakNaturally();
						last.removeMetadata("Damage", plugin);
						last = null;
					}
				}
				
				
			}
				 //if(!transparent.contains(last.getType())) {
					
				 //}
				 //this.locationprevious = entity.getLocation().getBlock().getLocation().toVector();
				
			//}
			}catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
	}
	private class explodeWhennearby extends BukkitRunnable{

		public Creeper c;
		public explodeWhennearby(Creeper c){
			this.c = c;
		}
		@Override
		public void run() {
			if(c.isDead())
				this.cancel();
			try {
			if(Utils.nearestPlayer(c.getLocation(),4) != null) {
				c.removePotionEffect(PotionEffectType.INVISIBILITY);
				c.explode();
				this.cancel();
			}
			}catch(NullPointerException e) {
				
			}
			
		}
		
	}
	private class spooderWebs extends BukkitRunnable{

		public Spider spooder;
		spooderWebs(Spider spooder){
			this.spooder=spooder;
		}
		@Override
		public void run() {
			spooder.getLocation().getBlock().setType(Material.COBWEB);
			if(spooder.isDead())
				this.cancel();
		}
		
	}
	private class setTarget extends BukkitRunnable{

		private Mob entity;
		private int distance;
		setTarget(Mob entity,int distance){
			this.entity= entity;
			this.distance = distance;
		}
		@Override
		public void run() {
			if(entity.isDead())
				this.cancel();
			try {
				this.entity.setTarget(Utils.nearestPlayer(this.entity.getLocation(), this.distance));
				entity.teleport(Utils.LookAt(entity.getTarget().getLocation(),entity.getLocation()));
			}catch(NullPointerException e) {
				
			}
			
		}
		
	}
	private class phantomBomber extends BukkitRunnable{

		private Phantom p;
		private Monster m;
		phantomBomber(Phantom p){
			this.p = p;
		}
		@Override
		public void run() {
			if(p.isDead())
				this.cancel();
			if(p.getPassengers().size() == 0) {
				p.setTarget(Utils.nearestMonster(p.getLocation()));
				return;
			}else {
				p.setTarget(Utils.nearestPlayer(p.getLocation(), 64));
			}
			
			Location loc = p.getTarget().getLocation().clone();
			loc.setY(p.getLocation().getY());
			try {
			if(p.getLocation().distance(loc) < 16) {
				if(p.getTarget() instanceof Monster) {
					p.addPassenger(p.getTarget());
					p.setTarget(null);
				}else {
					((LivingEntity)p.getPassengers().get(0)).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,200,1,true,false));
					p.eject();
					p.setTarget(null);
				}
				//((LivingEntity)p.getWorld().spawnEntity(p.getLocation(), Utils.randomMobOverworld())).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,200,1,true,false));
			}
			}catch(IllegalArgumentException e) {
				p.setTarget(Utils.nearestPlayer(p.getLocation(), 64));
			}
			
		}
		
	}
	
}
