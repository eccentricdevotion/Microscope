package ccl.microscope;

import org.bukkit.Rotation;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MicroscopeInteractListener implements Listener {

    private final Microscope plugin;

    MicroscopeInteractListener(Microscope plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != null && event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack is = player.getInventory().getItemInMainHand();
        if (is != null && LabEquipment.getByMaterial().containsKey(is.getType())) {
            // does it have item meta
            ItemMeta im = is.getItemMeta();
            if (im == null) {
                return;
            }
            if (!im.hasDisplayName()) {
                return;
            }
            if (!im.hasCustomModelData() || im.getCustomModelData() != 10000) {
                return;
            }
            // get the block
            Block block = event.getClickedBlock();
            if (block == null) {
                return;
            }
            if (!block.getType().isSolid()) {
                return;
            }
            Block up = block.getRelative(BlockFace.UP);
            if (!up.getType().isAir()) {
                return;
            }
            // place item frame with appropriate item
            World world = up.getWorld();
            ItemFrame frame = (ItemFrame) world.spawnEntity(up.getLocation(), EntityType.ITEM_FRAME);
            frame.setFacingDirection(BlockFace.UP);
            frame.setRotation(fromPlayersFacing(player));
            frame.setVisible(false);
            // clone the ItemStack and set the amount to 1
            ItemStack equipment = is.clone();
            equipment.setAmount(1);
            frame.setItem(equipment);
            // set the item that the microscope should display
            frame.getPersistentDataContainer().set(plugin.getMicroscopeKey(), PersistentDataType.INTEGER, 10000);
            // remove item from player's hand
            plugin.reduceInHand(player);
        }
    }

    private Rotation fromPlayersFacing(Player player) {
        switch (player.getFacing()) {
            case NORTH -> {
                return Rotation.FLIPPED;
            }
            case EAST -> {
                return Rotation.COUNTER_CLOCKWISE;
            }
            case SOUTH -> {
                return Rotation.NONE;
            }
            case WEST -> {
                return Rotation.CLOCKWISE;
            }
        }
        return Rotation.CLOCKWISE_45;
    }
}
