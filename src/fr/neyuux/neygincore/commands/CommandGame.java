package fr.neyuux.neygincore.commands;

import fr.neyuux.neygincore.CurrentGame;
import fr.neyuux.neygincore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandGame implements CommandExecutor {
	
	private final Core main;
	
	public CommandGame(Core main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(main.getPrefix() + "§8§l» §fListe des arguments de la commande §agame§f : \n§e/game get §7Donne le jeu actuel \n§e/game set <jeu> §7Change le jeu actuel");
			return true;
		}
		switch (args[0].toLowerCase()) {
			case "help":
				sender.sendMessage(main.getPrefix() + "§8§l» §fListe des arguments de la commande §agame§f : \n§e/game get §7Donne le jeu actuel \n§e/game set <jeu> §7Change le jeu actuel");
				break;
			case "get":
				sender.sendMessage(main.getPrefix() + "§8§l» §fJeu en cours : §c" + main.getCurrentGame().toString());
				break;
			case "set":
				if (sender.isOp()) {
					if (args.length < 2)
						sender.sendMessage(main.getPrefix() + "§8§l» §cVeuillez renseigner un jeu !");
					else {
						if (CurrentGame.getByPluginName(args[1]) != null)
							changeGame(sender, CurrentGame.getByPluginName(args[1]));
						else sender.sendMessage(main.getPrefix() + "§8§l» §cJeu invalide, liste de jeux : §ePvPKits, LG, None, UHC");
					}
				} else
					sender.sendMessage(main.getPrefix() + "§8§l» §cTu dois être §4OP §cpour lancer un jeu !");
				break;
			default:
				sender.sendMessage(main.getPrefix() + "§8§l» §cArgument invalide : essayez §e/game help §c!");
				break;
		}
		return true;
	}

	private void changeGame(CommandSender sender, CurrentGame cg) {
		main.setGame(cg);
		if (!cg.equals(CurrentGame.NONE))Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §b" + sender.getName() + " §ea mit le jeu sur " + cg.getPluginPrefix() + "§e.");
		else Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §b" + sender.getName() + " §ea reset le jeu.");
	}

}
