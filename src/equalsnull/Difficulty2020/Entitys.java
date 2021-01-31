package equalsnull.Difficulty2020;

import java.util.Collection;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class Entitys {
	public class Targeting implements Runnable{

		public Entity obj;
		public Entity target=null;
		public Targeting(Entity obj) {
			this.obj = obj;
		}
		
		@Override
		public void run() {
			try {
			if(target == null) {
				Collection<Entity> nearbyEntities =obj.getWorld().getNearbyEntities(obj.getLocation(), 8, 8, 8);
				for(Entity e : nearbyEntities) {
					if(e instanceof Player) {
						target = e;
					}
				}
			}
			obj.setVelocity(new Vector(target.getLocation().getX()-obj.getLocation().getX(),target.getLocation().getY()-obj.getLocation().getY(),target.getLocation().getZ()-obj.getLocation().getZ()).normalize());
			}catch(NullPointerException e) {
				
			}
		}
		
	}
	public class Skelly implements Runnable{

		public ProjectileSource skelly;
		Skelly(ProjectileSource skelly){
			this.skelly = skelly;
		}
		@Override
		public void run() {
			skelly.launchProjectile(Arrow.class);
			
		}
		
	}
	public class Blazer implements Runnable{

		public ProjectileSource blazer;
		Blazer(ProjectileSource blazer){
			this.blazer = blazer;
		}
		@Override
		public void run() {
			for(Entity e : ((Entity)blazer).getWorld().getNearbyEntities(((Entity)blazer).getLocation(), 8, 8, 8)) {
				if(e instanceof Player) {
					if(((LivingEntity)blazer).hasLineOfSight(e)) {
						blazer.launchProjectile(Fireball.class);
						return;
					}
				}
			}
			
			
		}
		
	}
}
