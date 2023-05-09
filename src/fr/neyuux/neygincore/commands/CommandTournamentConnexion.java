package fr.neyuux.neygincore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.neyuux.neygincore.Core;

public class CommandTournamentConnexion implements CommandExecutor {
	
	private final Core main;
	public CommandTournamentConnexion(Core main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (!main.getServer().getPluginManager().getPlugin("Tournoi").isEnabled()) {
			System.setProperty("TOURNAMENTCONNEXION", "TRUE");
			System.setProperty("RELOAD", "FALSE");
			main.getPluginLoader().enablePlugin(main.getServer().getPluginManager().getPlugin("Tournoi"));
			Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §eLe plugin §6§lTournoi §evient d'être démarré pour faire des inscriptions ! Il restera allumé pour 30 secondes.");
			return true;
		}
		
		return false;
	}

}
