package equalsnull.Difficulty2020;

import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
    	new PlayerListener(this);
    	new EntityListener(this);
    }
    @Override
    public void onDisable() {
    }
}
