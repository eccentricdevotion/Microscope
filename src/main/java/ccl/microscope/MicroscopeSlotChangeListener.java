package ccl.microscope;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class MicroscopeSlotChangeListener implements Listener {

    private final Microscope plugin;

    MicroscopeSlotChangeListener(Microscope plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStoredStacks().containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                player.getInventory().setItemInMainHand(plugin.getStoredStacks().get(player.getUniqueId()));
                plugin.getStoredStacks().remove(player.getUniqueId());
            }, 1L);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStoredStacks().containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStoredStacks().containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                player.getInventory().setItemInMainHand(plugin.getStoredStacks().get(player.getUniqueId()));
                plugin.getStoredStacks().remove(player.getUniqueId());
            }, 1L);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStoredStacks().containsKey(player.getUniqueId())) {
            player.getInventory().setItemInMainHand(plugin.getStoredStacks().get(player.getUniqueId()));
            plugin.getStoredStacks().remove(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStoredStacks().containsKey(player.getUniqueId())) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                player.getInventory().setItemInMainHand(plugin.getStoredStacks().get(player.getUniqueId()));
                plugin.getStoredStacks().remove(player.getUniqueId());
            }, 1L);
        }
    }
}
