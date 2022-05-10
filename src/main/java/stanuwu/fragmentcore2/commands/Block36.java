package stanuwu.fragmentcore2.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Block36 implements CommandExecutor, Listener {

    private final ItemStack piston = new ItemStack(Material.STICKY_PISTON);

    public Block36() {preparePiston();}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) sender.sendMessage("You must be a player to use this command");
        else player.getInventory().addItem(piston);
        return true;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemMeta itemMeta = event.getItemInHand().getItemMeta();
        if (!itemMeta.getDisplayName().equals("Block36")) return;
        if (itemMeta.getLore() == null) return;
        if (itemMeta.getLore().contains("Prevents FallingBlocks from turning into solid blocks")) {
            event.setCancelled(true);
            event.getBlockReplacedState().setType(Material.MOVING_PISTON);
            newArmorStand(event.getBlockReplacedState().getLocation().add(0.5, -0.4, 0.5));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getInteractionPoint() == null) return;
        Block block = event.getInteractionPoint().getBlock();
        if (block.getType() == Material.MOVING_PISTON) {
            event.setCancelled(true);
            block.getWorld().getNearbyEntities(block.getLocation().add(0.5, 0, 0.5), 0.5, 0.5, 0.5).forEach(entity -> {if (entity instanceof ArmorStand) entity.remove();});
            block.setType(Material.AIR);
            event.getPlayer().sendMessage("Removed Block36");
        }
    }

    private void preparePiston() {
        ItemMeta itemMeta = piston.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "Block36");
        itemMeta.setLore(List.of(ChatColor.RESET + "Prevents FallingBlocks from turning into solid blocks"));
        piston.setItemMeta(itemMeta);
    }

    private void newArmorStand(Location location) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setMarker(true);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCanMove(false);
        armorStand.setCanTick(false);
        armorStand.setInvulnerable(true);
        armorStand.setCollidable(false);
        armorStand.setAI(false);
        armorStand.getEquipment().setHelmet(piston);
    }
}
