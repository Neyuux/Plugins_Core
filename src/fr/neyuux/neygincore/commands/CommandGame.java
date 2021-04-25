package fr.neyuux.neygincore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.neyuux.neygincore.CurrentGame;
import fr.neyuux.neygincore.Index;

public class CommandGame implements CommandExecutor {
	
	private final Index main;
	
	public CommandGame(Index main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (args.length < 1) {
			sender.sendMessage(main.getPrefix() + "§8§l» §fListe des arguments de la commande §agame§f : \n§e/game get §7Donne le jeu actuel \n§e/game set <jeu> §7Change le jeu actuel");
			return true;
		}
		if (args[0].equalsIgnoreCase("help")) sender.sendMessage(main.getPrefix() + "§8§l» §fListe des arguments de la commande §agame§f : \n§e/game get §7Donne le jeu actuel \n§e/game set <jeu> §7Change le jeu actuel");
		
		if (args[0].equalsIgnoreCase("get")) {
			sender.sendMessage(main.getPrefix() + "§8§l» §fJeu en cours : §c" + main.getCurrentGame().toString());
		}
		
		else if (args[0].equalsIgnoreCase("set")) {
			if (sender.isOp()) {
				String g;
				if (args.length < 2)
					sender.sendMessage(main.getPrefix() + "§8§l» §cVeuillez renseigner un jeu !");
				else {
					g = args[1];
					setGame(sender, g);
				}
			} else
				sender.sendMessage(main.getPrefix() + "§8§l» §cTu dois être §4OP §cpour lancer un jeu !");
		}
		
		else {
			if (!args[0].equalsIgnoreCase("help")) sender.sendMessage(main.getPrefix() + "§8§l» §cArgument invalide : essayez §e/game help §c!");
		}
		return true;
		
	}
	
	public void setGame(CommandSender sender, String g) {
		if (g.equalsIgnoreCase("PvPKits")) {
			main.setGame("PvPKits", "PvPKits", CurrentGame.PVPKITS, new Location(Bukkit.getWorld("PvPKits"), -6.096, 5.1, -2.486, -89.6f, -0.5f));
			Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §b" + sender.getName() + " §ea mit le jeu sur §4§lP§b§lv§4§lP §9§lKits§e.");
		
		} else if (g.equalsIgnoreCase("LG")) {
			main.setGame("LG", "LG", CurrentGame.LG, new Location(Bukkit.getWorld("LG"), 494, 12.2, 307, 0f, 0f));
			Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §b" + sender.getName() + " §ea mit le jeu sur §4§lLoups§7-§4§lGarous §ede §6Thiercelieux§e.");
		
		} else if (g.equalsIgnoreCase("UHC")) {
			main.setGame("Core", "UHC", CurrentGame.UHC, new Location(Bukkit.getWorld("Core"), -565, 23.2, 850));
			Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §b" + sender.getName() + " §ea mit le jeu sur §e§lUHC§e.");
		}
		else if (g.equalsIgnoreCase("None")) {
			main.setGame(null, null, CurrentGame.NONE, null);
			Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §b" + sender.getName() + " §ea reset le jeu.");
		
		} else {
			sender.sendMessage(main.getPrefix() + "§8§l» §cJeu invalide, liste de jeux : §ePvPKits, LG, None, UHC");
		}
	}

}
