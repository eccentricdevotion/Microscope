package ccl.microscope;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

class MicroscopeUtils {

    static boolean hasSlideInHand(ItemStack is, Microscope plugin) {
        if (is == null) {
            return false;
        }
        if (!is.getType().equals(Material.GLASS)) {
            return false;
        }
        // does it have item meta
        ItemMeta im = is.getItemMeta();
        if (im == null) {
            return false;
        }
        if (!im.hasDisplayName()) {
            return false;
        }
        if (!im.hasCustomModelData()) {
            return false;
        }
        if (im.getCustomModelData() != 9999) {
            return false;
        }
        return im.getPersistentDataContainer().has(plugin.getMicroscopeKey(), PersistentDataType.INTEGER);
    }

    public static String uppercaseFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
