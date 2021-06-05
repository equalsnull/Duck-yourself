package equalsnull.Difficulty2020;

import java.util.Arrays;
import java.util.Collection;
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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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
			if(Math.random() > 0.95 && e.getWorld().getName().equalsIgnoreCase("world")) {
				e.getWorld().spawnEntity(e.getLocation(), EntityType.PHANTOM);
				e.remove();
				return ;
			}
			try {
				new setTarget(((Mob) e),32).runTaskTimer(plugin, 0, 60);
				new StareToMine((Mob) e).runTaskTimer(plugin, 0, 10);
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
			ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
			weapon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
			if(e.getType() == EntityType.DROWNED) {
				weapon = new ItemStack(Material.TRIDENT);
				weapon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
				weapon.addUnsafeEnchantment(Enchantment.LOYALTY, 1);
				weapon.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
			}
			weapon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
			ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
			helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack leggins = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
			boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			entity.getEquipment().setItemInMainHand(weapon);
			entity.getEquipment().setItemInMainHandDropChance(0);
			entity.getEquipment().setHelmet(helmet);
			entity.getEquipment().setHelmetDropChance(0);
			entity.getEquipment().setChestplate(chestplate);
			entity.getEquipment().setChestplateDropChance(0);
			entity.getEquipment().setLeggings(leggins);
			entity.getEquipment().setLeggingsDropChance(0);
			entity.getEquipment().setBoots(boots);
			entity.getEquipment().setBootsDropChance(0);
			// new StareToMine((Mob) e).runTaskTimer(plugin, 0, 10);
		}
		if(e instanceof PiglinAbstract) {
			LivingEntity entity = (LivingEntity)e;
			ItemStack weapon;
			if(Math.random() >= 0.5) {
				weapon = new ItemStack(Material.NETHERITE_AXE);
				weapon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
			}else {
				weapon = new ItemStack(Material.CROSSBOW);
				weapon.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 10);
				weapon.addUnsafeEnchantment(Enchantment.MULTISHOT, 10);
				weapon.addUnsafeEnchantment(Enchantment.PIERCING, 10);
			}
			
			ItemStack helmet = new ItemStack(Material.GOLDEN_HELMET);
			helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE);
			chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack leggins = new ItemStack(Material.GOLDEN_LEGGINGS);
			leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);
			boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			entity.getEquipment().setItemInMainHand(weapon);
			entity.getEquipment().setItemInMainHandDropChance(0);
			entity.getEquipment().setHelmet(helmet);
			entity.getEquipment().setHelmetDropChance(0);
			entity.getEquipment().setChestplate(chestplate);
			entity.getEquipment().setChestplateDropChance(0);
			entity.getEquipment().setLeggings(leggins);
			entity.getEquipment().setLeggingsDropChance(0);
			entity.getEquipment().setBoots(boots);
			entity.getEquipment().setBootsDropChance(0);
			// new StareToMine((Mob) e).runTaskTimer(plugin, 0, 10);
		}
		if(e instanceof Spider) {
			new spooderWebs((Spider)e).runTaskTimer(plugin, 0, 30);
			
		}
		if(e instanceof Phantom) {
			Phantom p = (Phantom)e;
			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,Integer.MAX_VALUE,1,true,false));
			//p.teleport(p.getLocation().add(0,32,0));
			new phantomBomber(p).runTaskTimer(plugin, 0, 200);
		}
		if(e instanceof ProjectileSource) {
			if(et == EntityType.SKELETON)
				new Shooter((ProjectileSource) e,Arrow.class).runTaskTimer(plugin, 0, 60);
			if(e instanceof Blaze)
				new Shooter((ProjectileSource) e,SmallFireball.class).runTaskTimer(plugin, 0, 60);
			if(e instanceof Ghast)
				new Shooter((ProjectileSource) e,LargeFireball.class).runTaskTimer(plugin, 0, 60);
			if(e instanceof EnderDragon)
				new Shooter((ProjectileSource) e,DragonFireball.class).runTaskTimer(plugin, 0, 60);
			if(e instanceof Witch) 
				new Shooter((ProjectileSource) e,ThrownPotion.class).runTaskTimer(plugin, 0, 60);
			if(e instanceof Drowned)
				new Shooter((ProjectileSource) e,Trident.class).runTaskTimer(plugin, 0, 60);
			if(e instanceof EnderDragon)
				new Shooter((ProjectileSource) e, DragonFireball.class).runTaskTimer(plugin, 0, 60);
		}
		if(e instanceof Projectile) {
			new Remove(e).runTaskTimer(plugin, 400, 400);
			if(!(((Projectile)e).getShooter() instanceof Player)) {
				try {
				new Targeting((Projectile)e,((Mob) ((Projectile) e).getShooter()).getTarget()).runTaskTimer(plugin,0,5);
				}catch(NullPointerException npe) {}
				((Projectile)e).setGravity(false);
				if(e instanceof ThrownPotion) {
					ThrownPotion tp = (ThrownPotion)e;
					ItemStack item = new ItemStack(Material.SPLASH_POTION);
					PotionMeta meta = (PotionMeta) item.getItemMeta();
					meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 200, 1), true);
					meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1), true);
					item.setItemMeta(meta);

					ThrownPotion thrownPotion = (ThrownPotion) e;
					thrownPotion.setItem(item);
				}
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
			DamageCause dc = event.getEntity().getLastDamageCause().getCause();
			if(dc == EntityDamageEvent.DamageCause.FIRE_TICK ||
				dc == EntityDamageEvent.DamageCause.LAVA ||
				dc == EntityDamageEvent.DamageCause.HOT_FLOOR) {
				event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.WITHER_SKELETON);
			}
		}
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		boolean cancle = true;
		if(event.getEntity() instanceof Player) {
			if(event.getDamager() instanceof Spider) {
				((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON,(int) (Math.random()*29.0)+1, 1, true, false, false));
			}
			cancle = false;
		}
		if(event.getDamager() instanceof Projectile) {
			if(((Projectile) event.getDamager()).getShooter() instanceof Player) {
				cancle = false;
			}else {
				event.getDamager().remove();
			}
		}
		if((event.getDamager() instanceof Player)) {
			cancle = false;
		}
		if(event.getEntityType() == EntityType.IRON_GOLEM) {
			Collection<Villager> villagerlist = event.getEntity().getWorld().getEntitiesByClass(org.bukkit.entity.Villager.class);
			for(Villager vi : villagerlist) {
				if(vi.getLocation().distance(event.getEntity().getLocation()) < 64) {
					Entity e = event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ILLUSIONER);
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,1,true,false));
					vi.remove();
				}
			}
		}
		if(event.getDamager() instanceof Trident) {
			if(((Trident)event.getDamager()).getItem().containsEnchantment(Enchantment.CHANNELING)) {
				event.getDamager().getWorld().strikeLightning(event.getEntity().getLocation());
			}
		}
		if(event.getEntityType() == EntityType.ENDER_DRAGON) {
			cancle = event.getEntity().getWorld().getEntitiesByClass(EnderCrystal.class).size() != 0;
		}
		event.setCancelled(cancle);
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
					new Remove(obj).runTaskTimer(plugin, 20, 0);
				}
				if(obj == null) {
					this.cancel();
				}
				if(obj.hasMetadata("Hit")) {
					new Remove(obj).runTaskTimer(plugin, 20, 0);
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
			if(obj.getLocation().distance(target.getLocation()) <= 2) {
				this.cancel();
				new Remove(obj).runTaskTimer(plugin, 20, 0);
			}
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
						Projectile proj = shooter.launchProjectile(projectile);
						proj.setShooter(shooter);
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
			if(!spooder.getWorld().getBlockAt(spooder.getLocation()).isLiquid())
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
			p.setTarget(Utils.nearestPlayer(p.getLocation(), 64));
			if(p.getTarget() == null){
				return;
			}
			//Location loc = p.getTarget().getLocation().clone();
			//loc.setY(p.getLocation().getY());
			
			if(Utils.distanceXZ(p.getLocation().clone(),p.getTarget().getLocation().clone()) < 16) {
				/*if(p.getTarget() instanceof Monster) {
					p.addPassenger(p.getTarget());
					p.setTarget(Utils.nearestPlayer(p.getLocation(), 64));
				}else {
					((LivingEntity)p.getPassengers().get(0)).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,200,1,true,false));
					p.eject();
					p.setTarget(null);
				}*/	
				EntityType et = Utils.randomMobOverworld();
				System.out.println(et.name()+" Spawn");
				((LivingEntity)p.getWorld().spawnEntity(p.getLocation(), et)).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,200,1,true,false));
			}
		}
		
	}
	private class Remove extends BukkitRunnable{

		Entity e;
		public Remove(Entity e) {
			this.e = e;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			e.remove();
			this.cancel();
		}
		
	}
}
