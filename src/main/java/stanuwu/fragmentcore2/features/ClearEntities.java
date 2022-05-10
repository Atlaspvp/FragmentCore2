package stanuwu.fragmentcore2.features;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import stanuwu.fragmentcore2.FragmentCore2;
import stanuwu.fragmentcore2.helpers.Helper;

public class ClearEntities implements CommandExecutor {

    public ClearEntities() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        int r = FragmentCore2.config.getInt("fragmentcore.cannoning.clearentity-range");
        int count = 0;
        for (Entity entity : player.getNearbyEntities(r, r, r)) {
            if (entity instanceof TNTPrimed || entity instanceof FallingBlock || entity instanceof ArmorStand) {
                count++;
                entity.remove();
            }
        }
        if (count < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("No Entities Found")));
        } else {
            if (count == 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix(1 + " Entity Cleared")));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix(count + " Entities Cleared")));
            }
        }
        return true;
    }
}