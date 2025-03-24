package icu.n501yhappy.catchplayers.Listeners;

import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class CatchLis implements Listener {

    @EventHandler
    public void player_catch(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getVehicle() != null) {
            return;
        }
        if (!player.isSneaking()) return;
        Entity entity = event.getRightClicked();
        if (entity == player) {
            return;
        }

        Entity topPassenger = getTop(player);
        if (entity.getPassengers().isEmpty() && !entity.equals(topPassenger)) {
            topPassenger.addPassenger(entity);
        } else {
            addPassenger(topPassenger, entity);
        }

        if (entity instanceof Bee) {
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }

    @EventHandler
    public void drop_1(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        if (player.getPassengers().isEmpty()) {
            return;
        }
        release(player, 1);
    }

    public void addPassenger(Entity below, Entity top) {
        if (below == top) {
            return;
        }
        if (top.getPassengers().isEmpty()) {
            below.addPassenger(top);
            return;
        }
        for (Entity passenger : top.getPassengers()) {
            addPassenger(below, passenger);
        }
        below.addPassenger(top);
    }

    public void release(Player player, double speed) {
        if (player.getPassengers().isEmpty()) {
            return;
        }
        Entity entity = player.getPassengers().get(0);
        if (player.isFlying() && entity instanceof Bee) {
            player.setFlying(false);
        }
        player.setAllowFlight(true);
        player.removePassenger(entity);
        Vector direction = player.getLocation().getDirection().normalize();
        entity.setVelocity(direction.multiply(speed));
    }

    @EventHandler
    public void Attacks(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (player.getPassengers().isEmpty()) {
            return;
        }
        release(player, 0);
    }

    @EventHandler
    public void PlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.isFlying()) {
            player.setFlying(false);
        }
        player.setAllowFlight(true);
    }

    public static Entity getTop(Entity entity) {
        if (entity.getPassengers().isEmpty()) {
            return entity;
        }
        return getTop(entity.getPassengers().get(entity.getPassengers().size() - 1));
    }
}