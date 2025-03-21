package icu.n501yhappy.catchplayers;

import icu.n501yhappy.catchplayers.Listeners.CatchLis;
import org.bukkit.plugin.java.JavaPlugin;

public final class CatchPlayers extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CatchLis(),this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
