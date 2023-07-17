package fr.neyuux.neygincore.commands;

import fr.neyuux.neygincore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandUpdateItems implements CommandExecutor {

    private final Core main;

    public CommandUpdateItems(Core core) {
        this.main = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            PlayerInventory inv = player.getInventory();

            ItemStack[] previous = inv.getContents();

            inv.clear();
            while (inv.firstEmpty() != -1)
                inv.setItem(inv.firstEmpty(), new ItemStack(Material.STONE));

            player.updateInventory();

            Bukkit.getScheduler().runTaskLater(main, () -> {
                inv.setContents(previous);
                player.updateInventory();
            }, 1L);
        }
        return true;
    }

}
