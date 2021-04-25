package fr.neyuux.neygincore.commands;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.neyuux.neygincore.Index;
import fr.neyuux.neygincore.tasks.SondageRunnable;

public class CommandSondage implements CommandExecutor {
	

	private Index main;
	
	public CommandSondage(Index main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("create")) {
					if (sender.isOp()) {
					if (args.length == 2) {
						sender.sendMessage(main.getPrefix() + "§8§l» §cIl faut 2 propositions au minimum !");
						return true;
					}
					if (args.length > 15) {
						sender.sendMessage(main.getPrefix() + "§8§l» §cIl faut 15 propositions au maximum !");
						return true;
					}
				String props = "";
				main.PlayersHaveVoted.clear();
				for (String s : args) {
					if (!s.equalsIgnoreCase(args[0])) {
						main.SondageList.put(s, 0);
						props = props + ", " + s;
					}
				}
				Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §fUn nouveau sondage à débuté ! Voici les propositions : §a" + props);
				Bukkit.broadcastMessage("§b§lSondage §8§l» §fVotez avec la commande : §e/sondage vote §c<proposition> §f!");
				SondageRunnable sond = new SondageRunnable(main);
				sond.timer = 30;
				sond.runTaskTimer(main, 0, 20);
				}
				} else if(args[0].equalsIgnoreCase("vote")) {
					if (!main.SondageList.isEmpty()) {
						if (main.SondageList.containsKey(args[1])) {
							if (!(sender instanceof Player)) {
								sender.sendMessage(main.getPrefix() + "§8§l» §cTu dois être un joueur pour faire cette commande !");
								return true;
							}
							
							Player p = (Player) sender;
							String s = "";
							
							if (!main.PlayersHaveVoted.contains(p.getUniqueId())) {
								main.PlayersHaveVoted.add(p.getUniqueId());
								main.SondageList.put(args[1], main.SondageList.get(args[1]) + 1);
								
								if (main.SondageList.get(args[1]) != 1) s = "s";
								
								p.sendMessage(main.getPrefix() + "§8§l» §fVous avez bien voté pour §c\"" + args[1] + "\" §f ! Cette proposition a désormais §a" + main.SondageList.get(args[1]) + " §fvote"+s+".");
							} else {
								sender.sendMessage(main.getPrefix() + "§8§l» §cVous avez déjà voté !");
							}
						} else {
							String props = "";
							for (Entry<String, Integer> en : main.SondageList.entrySet()) {
								props = props + ", " + en.getKey();
							}
							sender.sendMessage(main.getPrefix() + "§8§l» §cCette propostion n'existe pas ! Voici les propositons existantes : §e" + props);
						}
					} else {
						sender.sendMessage(main.getPrefix() + "§8§l» §cAucun vote n'est en cours !");
					}
				}
			} else {
				sender.sendMessage(main.getPrefix() + "§8§l» §fListe d'arguments pour la commande §asondage :\n§e/sondage vote <proposition> §7Voter pour une propostions\n§e/sondage create <propositions (2 min)>");
			}
		
		return true;
	}

}
