package equalsnull.Difficulty2020;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener  implements Listener{
	private Main  plugin;
	public PlayerListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onregenerating(EntityRegainHealthEvent event) {
		if(event.getRegainReason()== RegainReason.SATIATED) {
			//Bukkit.broadcastMessage("You are Satieated");
			((Player)event.getEntity()).setFoodLevel(19);
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onSleep(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		long time = player.getWorld().getTime();
		int food = player.getFoodLevel();
		int health = (int)player.getHealth();
		if(player.getHealth() == 20 ||
				player.getFoodLevel() < 6) {
			return;
		}
		if(player.getHealth() < 20 && food > 6) {
			int useableFood = food -6;
			int missinghealth = 20-health;
			if(useableFood == missinghealth) {
				player.setFoodLevel(food-useableFood);
				player.setHealth(health+missinghealth);
			}else if(useableFood > missinghealth) {
				player.setFoodLevel(food-missinghealth);
				player.setHealth(health+missinghealth);
			}else if(useableFood < missinghealth) {
				player.setFoodLevel(food-useableFood);
				player.setHealth(health+useableFood);
			}
		}
	}
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player p = (Player)event.getEntity();
			if(Math.random()> 0.95 && p.getEquipment().getItemInMainHand().getType() != Material.AIR) {
				
				p.getWorld().dropItemNaturally(p.getLocation(),p.getEquipment().getItemInMainHand());
				p.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
			}
		}
	}
}
