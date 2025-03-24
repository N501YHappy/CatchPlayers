package icu.n501yhappy.catchplayers;

import icu.n501yhappy.catchplayers.Listeners.CatchLis;
import org.bukkit.Bukkit;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public final class CatchPlayers extends JavaPlugin {
    private BukkitTask periodicTask;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CatchLis(),this);
        periodicTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getPassengers().isEmpty() && hasChicken(p.getPassengers())) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 2, countChickens(p)-1, false, false));
                    }
                    if (p.isFlying() && !p.getPassengers().isEmpty() && !hasBee(p.getPassengers())){
                        p.setFlying(false);
                        p.setAllowFlight(true);
                    }
                }
            }
        }.runTaskTimer(this, 0, 40);
        printPluginInfo();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        if (periodicTask != null) {
            periodicTask.cancel();
        }
        // Plugin shutdown logic
    }
    public static int countChickens(Entity entity) {
        if (entity == null)  return 0;
        int count = 0;
        if (entity instanceof Chicken) {
            count = 1;
        }
        for (Entity passenger : entity.getPassengers()) {
            count += countChickens(passenger);
        }
        return count;
    }
    public static boolean hasChicken(List<Entity> list) {
        if (list == null || list.isEmpty()) return false;
        Entity firstEntity = list.get(0);
        if (firstEntity instanceof Chicken) return true;
        if (firstEntity.getPassengers().isEmpty()) return false;
        return hasChicken(firstEntity.getPassengers());
    }
    public static int countBees(Entity entity) {
        if (entity == null)  return 0;
        int count = 0;
        if (entity instanceof Bee) {
            count = 1;
        }
        for (Entity passenger : entity.getPassengers()) {
            count += countBees(passenger);
        }
        return count;
    }
    public static boolean hasBee(List<Entity> list) {
        if (list == null || list.isEmpty()) return false;
        Entity firstEntity = list.get(0);
        if (firstEntity instanceof Bee) return true;
        if (firstEntity.getPassengers().isEmpty()) return false;
        return hasBee(firstEntity.getPassengers());
    }
    private void printPluginInfo() {
        String pluginName = getDescription().getName();
        String version = getDescription().getVersion();
        String authors = String.join(", ", getDescription().getAuthors());
        String qqGroup = "1042508946";
        String githubUrl = "https://github.com/n501yhappy/CatchPlayers";
        String notice = "感谢使用CatchPlayers插件！如有问题或建议，请加入QQ群或访问GitHub页面发布issues (心情好了我会去看的)";

        // 定义颜色代码
        final String RESET = "§r";
        final String BLUE = "§9";
        final String GREEN = "§a";
        final String YELLOW = "§e";
        final String RED = "§c";

        // 构建表格
        StringBuilder table = new StringBuilder();
        table.append(BLUE).append("--------------------------------------------------").append(RESET).append("\n");
        table.append("|").append(GREEN).append(String.format("%32s", pluginName)).append(RESET).append("|\n");
        table.append(BLUE).append("--------------------------------------------------").append(RESET).append("\n");
        table.append("| ").append(YELLOW).append("Version:       ").append(RESET).append(version).append(" |\n");
        table.append("| ").append(YELLOW).append("Authors:       ").append(RESET).append(authors).append(" |\n");
        table.append("| ").append(YELLOW).append("QQ Group:      ").append(RESET).append(qqGroup).append(" |\n");
        table.append("| ").append(YELLOW).append("GitHub:        ").append(RESET).append(githubUrl).append(" |\n");
        table.append(BLUE).append("--------------------------------------------------").append(RESET).append("\n");
        table.append("| ").append(YELLOW).append("Notice:        ").append(RESET).append(notice).append(" |\n");
        table.append(BLUE).append("--------------------------------------------------").append(RESET);

        // 输出到控制台
        Bukkit.getConsoleSender().sendMessage(table.toString().split("\n"));
    }
}
