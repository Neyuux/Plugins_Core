package fr.neyuux.neygincore.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandUpdateItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (sender instanceof Player) {
            PlayerInventory inv = ((Player)sender).getInventory();

            for (ItemStack content : inv.getContents()) {
                Material type = content.getType();
                content.setType(Material.STONE);
                content.setType(type);
            }
        }
        return true;
    }

}
