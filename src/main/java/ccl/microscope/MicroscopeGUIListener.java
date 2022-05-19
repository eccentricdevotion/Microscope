package ccl.microscope;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class MicroscopeGUIListener implements Listener {

    private final Microscope plugin;

    MicroscopeGUIListener(Microscope plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (view.getTitle().equals(ChatColor.DARK_RED + "Slides") || view.getTitle().equals(ChatColor.DARK_RED + "Computer Storage")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getRawSlot();
            // for now at least not slot 0 as it is an empty slide/screen...
            if (slot > 0 && slot < 54) {
                if (slot == 53) {
                    // close
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, player::closeInventory, 1L);
                } else {
                    ItemStack is = event.getInventory().getItem(slot);
                    if (is != null) {
                        // add slid/screen to player's inventory
                        ItemStack ss = is.clone();
                        ItemMeta ssMeta = ss.getItemMeta();
                        ssMeta.getPersistentDataContainer().set(plugin.getMicroscopeKey(), PersistentDataType.INTEGER, slot + 10000);
                        ss.setItemMeta(ssMeta);
                        HashMap<Integer, ItemStack> items = player.getInventory().addItem(ss);
                        if (items.size() > 0) {
                            player.getWorld().dropItem(player.getLocation(), ss);
                        }
                    }
                }
            }
        }
    }
}
