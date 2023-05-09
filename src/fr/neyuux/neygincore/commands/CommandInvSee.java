package fr.neyuux.neygincore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.neyuux.neygincore.Core;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandInvSee implements CommandExecutor {

    private final Core main;

    public CommandInvSee(Core main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = main.getPrefix() + "§8§l» §b§lInvSee §8§l» ";

        if (cmd.getName().equalsIgnoreCase("invsee")) {
            Player p = (Player)sender;

            if (args.length > 0) {

                if (p.isOp()) {

                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);

                    if (Bukkit.getPlayer(args[0]) == null) {

                        p.playSound(p.getLocation(), Sound.BAT_DEATH, 1.0F, -1.0F);
                        p.sendMessage(prefix + "§cLe joueur §e\"§4" + args[0] + "§e\"§c n'est pas en ligne.");

                    } else {

                        p.openInventory(targetPlayer.getInventory());
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, -10.0F);

                    }
                } else {

                    p.sendMessage(prefix + "§cVous n'avez pas la permissino !");
                    p.playSound(p.getLocation(), Sound.BAT_DEATH, 1.0F, -10.0F);
                }
            } else {
                p.sendMessage(prefix + "§cUsage : §6/" + label +  " §e<joueur>");
                p.playSound(p.getLocation(), Sound.BAT_DEATH, 1.0F, -1.0F);
            }
        }
        return true;
    }

}
