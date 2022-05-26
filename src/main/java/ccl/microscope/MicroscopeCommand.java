package ccl.microscope;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MicroscopeCommand implements CommandExecutor, TabCompleter {

    private final ImmutableList<String> GIVE_SUBS = ImmutableList.of("MICROSCOPE", "SLIDE_RACK", "ELECTRON_MICROSCOPE", "COMPUTER_MONITOR", "TELESCOPE", "FILING_CABINET");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("microscope")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage("Command can only be used by a player!");
                return true;
            }
            if (!player.hasPermission("microscope.use")) {
                sender.sendMessage("You do not have permission to use the Microscope command!");
                return true;
            }
            if (args.length < 2) {
                return false;
            }
            // give lab equipment
            try {
                LabEquipment equipment = LabEquipment.valueOf(args[1].toUpperCase(Locale.ROOT));
                ItemStack is = new ItemStack(equipment.material, 1);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(MicroscopeUtils.uppercaseFirst(equipment.toString()).replace("_", " "));
                im.setCustomModelData(10000);
                is.setItemMeta(im);
                player.getInventory().addItem(is);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        String lastArg = args[args.length - 1];
        if (args.length <= 1) {
            return ImmutableList.of("give");
        } else if (args.length == 2) {
            return partial(lastArg, GIVE_SUBS);
        }
        return null;
    }

    private List<String> partial(String token, Collection<String> from) {
        return StringUtil.copyPartialMatches(token, from, new ArrayList<>(from.size()));
    }

}
