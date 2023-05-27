package fr.neyuux.neygincore.commands;

import fr.neyuux.neygincore.CurrentGame;
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

		if (!CurrentGame.TOURNOI.isDetected()) {
			sender.sendMessage("�4[�cErreur�4] �cImpossible d'effectuer cette commande, le plugin Tournoi n'est pas activ�.");
		}
		
		if (!main.getServer().getPluginManager().getPlugin("Tournoi").isEnabled()) {
			System.setProperty("TOURNAMENTCONNEXION", "TRUE");
			System.setProperty("RELOAD", "FALSE");
			main.getPluginLoader().enablePlugin(main.getServer().getPluginManager().getPlugin("Tournoi"));
			Bukkit.broadcastMessage(main.getPrefix() + "�8�l� �eLe plugin �6�lTournoi �evient d'�tre d�marr� pour faire des inscriptions ! Il restera allum� pour 30 secondes.");
			return true;
		}
		
		return false;
	}

}
