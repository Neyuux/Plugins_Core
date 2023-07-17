package fr.neyuux.neygincore.commands;

import fr.neyuux.neygincore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandProtections implements CommandExecutor {

    private final Core main;

    public CommandProtections(Core core) {

        this.main = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player) || ((Player)sender).getUniqueId().toString().equals("0234db8c-e6e5-45e5-8709-ea079fa575bb")) {

            sender.sendMessage(main.getPrefix() + "§8§l» §aProtections de Neyuux_ " + (main.isProtections() ? "désactivées" : "activées") + ".");
            main.setProtections(!main.isProtections());

        }

        return true;
    }
}
