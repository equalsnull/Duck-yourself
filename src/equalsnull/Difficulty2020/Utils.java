package equalsnull.Difficulty2020;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Utils {

	public static Player nearestPlayer(Location loc) {
		return nearestPlayer(loc,1000,true);
	}
	public static Player nearestPlayer(Location loc,double maxdistance) {
		return nearestPlayer(loc, maxdistance, false);
	}
	public static Player nearestPlayer(Location loc,double maxdistance,boolean seeinvis) {
		return nearestPlayer(loc, maxdistance, seeinvis,false);
	}
	public static Player nearestPlayer(Location loc,double maxdistance,boolean seeinvis,boolean seecreative) {
		Player nearest = null;
		double dist = maxdistance;
		try {
		for(Player p:Bukkit.getOnlinePlayers()) {
			if(p.getLocation().distance(loc) < dist && !p.isDead()
				&&  ((!p.hasPotionEffect(PotionEffectType.INVISIBILITY) && hasArmor(p))|| !seeinvis)
				) {//&&  (p.getGameMode() != GameMode.CREATIVE) || seecreative) {
				nearest = p;
				dist = p.getLocation().distance(loc);
			}
		}
		}catch(IllegalArgumentException e) {
			
		}
		return nearest;
	}
	public static Monster nearestMonster(Location loc) {
		Monster nearest = null;
		double dist = 64;
		try {
			Collection<Entity> e = loc.getWorld().getNearbyEntities(loc, 64, 64, 64);
		for(Entity p:e) {
			if(p.getLocation().distance(loc) < dist && !p.isDead() && e instanceof Monster) {
				nearest = (Monster)p;
				dist = p.getLocation().distance(loc);
			}
		}
		}catch(IllegalArgumentException e) {
			
		}
		return nearest;
	}
	public static EntityType randomMobOverworld() {
		double random = Math.random();
		if(random < 0.20) {
			return EntityType.ZOMBIE;
		}else if(random < 0.5) {
			return EntityType.SKELETON;
		}else if(random < 0.8) {
			return EntityType.CREEPER;
		}else{
			return EntityType.WITCH;
		}
	}
	public static Location LookAt(Location lookat, Location me)
	{
	    double dirx = me.getX() - lookat.getX();
	    double diry = me.getY() - lookat.getY();
	    double dirz = me.getZ() - lookat.getZ();

	    double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

	    dirx /= len;
	    diry /= len;
	    dirz /= len;

	    double pitch = Math.asin(diry);
	    double yaw = Math.atan2(dirz, dirx);

	    //to degree
	    pitch = pitch * 180.0 / Math.PI;
	    yaw = yaw * 180.0 / Math.PI;

	    yaw += 90f;
	    me.setPitch((float)pitch);
	    me.setYaw((float)yaw);
	    return me;
	}
	public static Vector axify(Vector vec) {
		float x = Math.abs((float)vec.getX());
		float y = Math.abs((float)vec.getY());
		float z = Math.abs((float)vec.getZ());
		if(x > y && x > z) {
			return(new Vector(x>0?1:-1,0,0));
		}
		if(y > x && y > z) {
			return(new Vector(0,y>0?1:-1,0));
		}
		if(z > y && z > x) {
			return(new Vector(0,0,x>0?1:-1));
		}
		return new Vector();
	}
	public static boolean hasArmor(LivingEntity e) {
		EntityEquipment eq = e.getEquipment();
		try {
		if(eq.getHelmet().getType() == Material.AIR &&
		   eq.getChestplate().getType() == Material.AIR &&
		   eq.getLeggings().getType() == Material.AIR &&
		   eq.getBoots().getType() == Material.AIR )
		return false;
		return true;
		}catch(NullPointerException exc) {
			return false;
		}
	}
	public static double distanceXZ(Location loc1, Location loc2) {
		loc2.setY(loc1.getY());
		return loc1.distance(loc2);
	}
}
