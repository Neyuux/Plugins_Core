package fr.neyuux.neygincore.commands;

import fr.neyuux.neygincore.CurrentGame;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.neyuux.neygincore.Core;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandBroadcast implements CommandExecutor {
	
	private final Core main;
	
	public CommandBroadcast(Core main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
	String prefixannonce = "�9[�c�lAnnonce�r�9]";
		if (args.length == 0)
			sender.sendMessage(main.getPrefix() + "�8�l� �4[�cErreur�4] �c Il faut rajouter un message � envoyer ! �6�o(/broadcast <message>)");
		else {
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage(prefixannonce + " �f" + Arrays.stream(args).map(part -> part + " ").collect(Collectors.joining()));
			Bukkit.broadcastMessage("");
		}
		return true;
	}

}
