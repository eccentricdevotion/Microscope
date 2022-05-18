package ccl.microscope;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MicroscopeItemFrameListener implements Listener {

    private final Microscope plugin;

    MicroscopeItemFrameListener(Microscope plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof ItemFrame frame) {
            if (!frame.getPersistentDataContainer().has(plugin.getMicroscopeKey(), PersistentDataType.INTEGER)) {
                return;
            }
            event.setCancelled(true);
            frame.setRotation(getPreviousRotation(frame.getRotation()));
            // get the item in the frame
            ItemStack dye = frame.getItem();
            if (dye == null) {
                return;
            }
            if (!LabEquipment.getByMaterial().containsKey(dye.getType())) {
                return;
            }
            Player player = event.getPlayer();
            ItemStack is = player.getInventory().getItemInMainHand();
            LabEquipment equipment = LabEquipment.getByMaterial().get(dye.getType());
            switch (equipment) {
                case SLIDE_RACK -> {
                    // if hand contains a slide return it
                    if (MicroscopeUtils.hasSlideInHand(is, plugin)) {
                        plugin.reduceInHand(player);
                    } else {
                        // open the slide GUI
                        ItemStack[] slides = new SlideInventory(plugin).getItems();
                        Inventory inventory = plugin.getServer().createInventory(player, 54, ChatColor.DARK_RED + "Slides");
                        inventory.setContents(slides);
                        player.openInventory(inventory);
                    }
                }
                case MICROSCOPE -> {
                    // microscope
                    int cmd;
                    if (MicroscopeUtils.hasSlideInHand(is, plugin)) {
                        // set microscope slide
                        cmd = is.getItemMeta().getPersistentDataContainer().get(plugin.getMicroscopeKey(), PersistentDataType.INTEGER);
                        frame.getPersistentDataContainer().set(plugin.getMicroscopeKey(), PersistentDataType.INTEGER, cmd);
                    } else {
                        // view current slide
                        cmd = frame.getPersistentDataContainer().get(plugin.getMicroscopeKey(), PersistentDataType.INTEGER);
                    }
                    // remember item in hand for restoration
                    plugin.getStoredStacks().put(player.getUniqueId(), is);
                    // set item in hand
                    ItemStack slide = new ItemStack(Material.GLASS, 1);
                    ItemMeta slideMeta = slide.getItemMeta();
                    slideMeta.setDisplayName(Slide.values()[cmd >= 10000 ? cmd - 10000 : cmd].getName());
                    slideMeta.setCustomModelData(cmd);
                    slide.setItemMeta(slideMeta);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> player.getInventory().setItemInMainHand(slide), 1L);
                }
                default -> {
                    // electron microscope
                }
            }
        }
    }

    private Rotation getPreviousRotation(Rotation rotation) {
        int max = Rotation.values().length - 1;
        int ord = rotation.ordinal() - 1;
        if (ord < 0) {
            ord = max;
        }
        return Rotation.values()[ord];
    }
}
