package ccl.microscope;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Microscope extends JavaPlugin {

    private NamespacedKey microscopeKey;
    private HashMap<UUID, ItemStack> storedStacks = new HashMap<>();

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MicroscopeInteractListener(this), this);
        pm.registerEvents(new MicroscopeItemFrameListener(this), this);
        pm.registerEvents(new MicroscopeSlotChangeListener(this), this);
        pm.registerEvents(new MicroscopeDamageListener(this), this);
        pm.registerEvents(new MicroscopeGUIListener(this), this);
        pm.registerEvents(new MicroscopePlaceListener(this), this);
        getCommand("microscope").setExecutor(new MicroscopeCommand(this));
        microscopeKey = new NamespacedKey(this, "microscope");
    }

    NamespacedKey getMicroscopeKey() {
        return microscopeKey;
    }

    HashMap<UUID, ItemStack> getStoredStacks() {
        return storedStacks;
    }

    void reduceInHand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        int count = itemStack.getAmount();
        if (count - 1 > 0) {
            itemStack.setAmount(count - 1);
        } else {
            itemStack = null;
        }
        player.getInventory().setItemInMainHand(itemStack);
    }
}