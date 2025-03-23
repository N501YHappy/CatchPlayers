package icu.n501yhappy.catchplayers.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;// --extensions-dir "E:\VSCode-win32-x64-1.97.2\extensions"
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class CatchLis implements Listener {
    @EventHandler
    public void player_catch(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getVehicle() != null) {
            return;
        }
        if (!player.isSneaking() || !player.getPassengers().isEmpty()) return;

        Entity entity = event.getRightClicked();
        if (entity.getPassengers().isEmpty()) {
            player.addPassenger(entity);
        } else {
            addPassenger(player, entity);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        if (player.getPassengers().isEmpty()) return;
        release(player,1);
    }
    public void addPassenger(Entity below, Entity top) {//dg
        
        for (Entity passenger : top.getPassengers()) {
            addPassenger(below, passenger);
        }
        below.addPassenger(top);
    }
    public void release(Player player,double speed){
        Entity entity = player.getPassengers().get(0);
        player.removePassenger(entity);
        Vector direction = player.getLocation().getDirection().normalize();
        entity.setVelocity(direction.multiply(speed));
    }
    @EventHandler
    public void Attacks(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getEntity().getPassengers() != null) release((Player) event.getEntity(),0);
    }
}