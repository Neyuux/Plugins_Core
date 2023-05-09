package fr.neyuux.neygincore.commands;

import fr.neyuux.neygincore.Core;
import fr.neyuux.neygincore.tasks.PollRunnable;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandSondage implements CommandExecutor {
	

	private final Core main;

	public static HashMap<String, List<UUID>> votes = new HashMap<>();
	
	public CommandSondage(Core main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("create")) {
					if (sender.isOp()) {
						if (args.length == 2) {
							sender.sendMessage(main.getPrefix() + "§8§l§ §cIl faut 2 propositions au minimum !");
							return true;
						}
						if (args.length > 15) {
							sender.sendMessage(main.getPrefix() + "§8§l§ §cIl faut 15 propositions au maximum !");
							return true;
						}
						Bukkit.broadcastMessage(main.getPrefix() + "§8§l§ §fUn nouveau sondage § d§but§ ! Voici les propositions : ");
						votes.clear();
						for (int i=1; i < args.length; i++) {
							String s = getRandomColor() + args[i];
							votes.put(s, new ArrayList<>());
							Bukkit.broadcastMessage(" §0- §f" + s);
						}
						Bukkit.broadcastMessage("§b§lSondage §8§l§ §fVotez avec la commande : §e/sondage vote §c<proposition> §f!");
						new PollRunnable(main).runTaskTimer(main, 0, 20);
					} else sender.sendMessage(main.getPrefix() + "§8§l§ §cVous n'avez pas la permission de cr§er un sondage.");
				} else if(args[0].equalsIgnoreCase("vote")) {
					if (!votes.isEmpty())
						if (containsArg(args[1])) {
							if (!(sender instanceof Player)) {
								sender.sendMessage(main.getPrefix() + "§8§l§ §cTu dois §tre un joueur pour faire cette commande !");
								return true;
							}
							Player p = (Player) sender;
							String s = "";

							if (!hasVoted(p.getUniqueId())) {
								votes.get(getWithColor(args[1])).add(p.getUniqueId());
								if (votes.get(getWithColor(args[1])).size() != 1) s = "s";
								p.sendMessage(main.getPrefix() + "§8§l§ §fVous avez bien vot§ pour §c\"" + args[1] + "\" §f ! Cette proposition a d§sormais §a" + votes.get(getWithColor(args[1])).size() + " §fvote" + s + ".");
							} else sender.sendMessage(main.getPrefix() + "§8§l§ §cVous avez d§j§ vot§ !");
					} else {
						sender.sendMessage(main.getPrefix() + "§8§l§ §cCette propostion n'existe pas ! Voici les propositons existantes : ");
						for (String v : votes.keySet())
							sender.sendMessage(" §0- §f" + v);
					}
					else sender.sendMessage(main.getPrefix() + "§8§l§ §cAucun vote n'est en cours !");
				} else sender.sendMessage(main.getPrefix() + "§8§l§ §fListe d'arguments pour la commande §asondage :\n§e/sondage vote <proposition> §7Voter pour une propostions\n§e/sondage create <propositions (2 min)>");
			} else
				sender.sendMessage(main.getPrefix() + "§8§l§ §fListe d'arguments pour la commande §asondage :\n§e/sondage vote <proposition> §7Voter pour une propostions\n§e/sondage create <propositions (2 min)>");
		return true;
	}


	private static ChatColor getRandomColor() {
		ChatColor c = ChatColor.values()[new Random().nextInt(ChatColor.values().length)];
		for (String s : votes.keySet()) if (s.startsWith(c.toString())) return getRandomColor();
		if (c.equals(ChatColor.STRIKETHROUGH) || c.equals(ChatColor.RESET) || c.equals(ChatColor.MAGIC) || c.equals(ChatColor.BLACK))
			return getRandomColor();
		return c;
	}

	private static boolean containsArg(String s) {
		for (String v : votes.keySet())
			if (v.substring(2).equalsIgnoreCase(s))
				return true;
		return false;
	}

	private static boolean hasVoted(UUID id) {
		for (List<UUID> list : votes.values())
			for (UUID vid : list)
				if (vid.equals(id))
					return true;
		return false;
	}

	private String getWithColor(String s) {
		HashMap<String, Double> percent = new HashMap<>();
		for (String v : votes.keySet()) percent.put(v, StringUtils.getJaroWinklerDistance(s, v));
		while (percent.size() != 1) {
			List<Map.Entry<String, Double>> en = new ArrayList<>(percent.entrySet());
			int c = Double.compare(en.get(0).getValue(), en.get(1).getValue());
			if (c == 0 || c < 0) percent.remove(en.get(0).getKey());
			else percent.remove(en.get(1).getKey());
		}
		return new ArrayList<>(percent.keySet()).get(0);
	}

}
