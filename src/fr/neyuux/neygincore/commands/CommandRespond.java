package fr.neyuux.neygincore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.neyuux.neygincore.Core;

public class CommandRespond implements CommandExecutor {
	
	private final Core main;
	
	public CommandRespond(Core main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (CommandTell.lastMessages.containsKey(player)) {
				Player p = CommandTell.lastMessages.get(player);
				String help = "§fAide pour la commande §e"+alias+"§f :§r\n§e/"+alias+" §a <message>";
				
				if (args.length > 0) {
					StringBuilder bc = new StringBuilder();
					for (String part : args) bc.append(part).append(" ");
					
					p.sendMessage("§5[§bReçu de §a§l" + player.getName() + "§5] §8§l» §e" + bc);
					CommandTell.lastMessages.put(p, player);
					player.sendMessage("§5[§bEnvoyé à §a§l" + p.getName() + "§5] §8§l» §e" + bc);
				} else
					player.sendMessage(main.getPrefix() + "§8§l» " + help);
			} else
				player.sendMessage(main.getPrefix() + "§8§l» §cVous n'avez personne à qui répondre !");
		} else
			sender.sendMessage(main.getPrefix() + "§8§l» §cVous n'êtes pas un joueur !");
		
		return true;
	}

}
