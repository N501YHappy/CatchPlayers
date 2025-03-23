package icu.n501yhappy.catchplayers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import icu.n501yhappy.catchplayers.Listeners.CatchLis;
import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class CatchPlayers extends JavaPlugin {
    private ProtocolManager protocolManager;
    private BukkitTask periodicTask;
    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        getServer().getPluginManager().registerEvents(new CatchLis(),this);
        periodicTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getPassengers().isEmpty() && p.getPassengers().get(0) instanceof Chicken) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 2, 1, false, false));
                    }
                }
            }
        }.runTaskTimer(this, 0, 40);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        if (periodicTask != null) {
            periodicTask.cancel();
        }
        // Plugin shutdown logic
    }
}
