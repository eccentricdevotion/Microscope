package ccl.microscope;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class MicroscopePlaceListener implements Listener {

    private final Microscope plugin;

    MicroscopePlaceListener(Microscope plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getBlock().getType().equals(Material.GLASS)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack is = player.getInventory().getItemInMainHand();
        event.setCancelled(MicroscopeUtils.hasSlideInHand(is, plugin));
    }
}
