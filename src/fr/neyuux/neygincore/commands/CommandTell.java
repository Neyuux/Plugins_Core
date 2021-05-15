package fr.neyuux.neygincore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.neyuux.neygincore.Index;

import java.util.HashMap;

public class CommandTell implements CommandExecutor {
	
	private final Index main;

	public static final HashMap<Player, Player> lastMessages = new HashMap<>();
	
	public CommandTell(Index main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		String help = "§fAide pour la commande §e"+alias+"§f :§r\n§e/"+alias+" §a<pseudo> <message>";
		
		if (args.length == 0) sender.sendMessage(main.getPrefix() + "§8§l» " + help);
		else {
			if (args[0].equalsIgnoreCase("help")) sender.sendMessage(main.getPrefix() + "§8§l» " + help);
			else {
				Player p = null;
				StringBuilder msg = new StringBuilder();
				for(Player op : Bukkit.getOnlinePlayers())
					if (op.getName().equalsIgnoreCase(args[0]))
						p = op;

				if (p == null)
					sender.sendMessage(main.getPrefix() + "§8§l» §cLe joueur §4\"§e" + args[0] + "§4\"§c n'existe pas.");
				else if (p.getName().equals(sender.getName()))
					sender.sendMessage(main.getPrefix() + "§8§l» §cSchizophrène.");

				else {
					for (String s : args) if (!s.equals(args[0])) msg.append(" ").append(s);
					p.sendMessage("§5[§bReçu de §a§l" + sender.getName() + "§5] §8§l» §e" + msg);
					if (sender instanceof Player) lastMessages.put(p, (Player) sender);
					sender.sendMessage("§5[§bEnvoyé à §a§l" + p.getName() + "§5] §8§l» §e" + msg);
					if (sender instanceof Player) lastMessages.put((Player) sender, p);
				}
			}
		}
		return true;
	}

}
