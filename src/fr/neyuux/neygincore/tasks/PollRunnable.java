package fr.neyuux.neygincore.tasks;

import fr.neyuux.neygincore.Core;
import fr.neyuux.neygincore.commands.CommandSondage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PollRunnable extends BukkitRunnable {
	
	private final Core main;
	private static int timer;
	
	public PollRunnable(Core main) {
		this.main = main;
		timer = 30;
	}
	
	@Override
	public void run() {
		if (timer == 0) {
			List<String> props = new ArrayList<>(CommandSondage.votes.keySet());
			while (props.size() != 1) {
				int c = Integer.compare(CommandSondage.votes.get(props.get(0)).size(), CommandSondage.votes.get(props.get(1)).size());
				if (c == 0) props.remove(new Random().nextInt(2));
				if (c > 0) props.remove(1);
				if (c < 0) props.remove(0);
			}
			Bukkit.broadcastMessage("§b§lSondage §8§l§ §eR§sultats :");
			Bukkit.broadcastMessage("-------------------------------");
			for (Map.Entry<String, List<UUID>> en : CommandSondage.votes.entrySet())
				Bukkit.broadcastMessage(" §0\u25a0 §f" + en.getKey() + " §1(§3" + en.getValue().size() + " §bvote" + (en.getValue().size() == 1 ? "" : "s") + "§1)");
			Bukkit.broadcastMessage("-------------------------------");
			Bukkit.broadcastMessage("§b§lSondage §8§l§ §e§lVictoire de la proposition §6\"" + props.get(0) + "§6\" §e!");
			for (Player p : Bukkit.getOnlinePlayers()) {
				Core.sendTitle(p, "§b§lSondage termin§", "§eVictoire de la proposition " + props.get(0) + " §e!", 5, 60, 5);
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 1);
			}
			cancel();
			return;
		}
		for (Player p : Bukkit.getOnlinePlayers())
			Core.sendActionBar(p, main.getPrefix() + "§8§l§ §bTemps restant pour le sondage : §f" + timer + " seconde" + (timer == 1 ? "" : "s"));
		timer--;
	}

}
