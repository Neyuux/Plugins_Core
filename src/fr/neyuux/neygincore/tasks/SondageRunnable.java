package fr.neyuux.neygincore.tasks;

import fr.neyuux.neygincore.Index;
import fr.neyuux.neygincore.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.Map.Entry;

public class SondageRunnable extends BukkitRunnable {

	public int timer = 30;
	private final HashMap<String, Integer> couleurs = new HashMap<String, Integer>();
	private final List<String> votes = new ArrayList<String>();
	
	private final Index main;
	
	public SondageRunnable(Index main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		
		if (main.SondageList.isEmpty()) {
			cancel();
			return;
		}
		
		if (timer == 30) {
			for (Player p : Bukkit.getOnlinePlayers()) {
			ScoreboardSign scoreboard = new ScoreboardSign(p, "§b§lSondage");
			int slines = 0;
			scoreboard.create();
			for(Entry<String, Integer> en : main.SondageList.entrySet()) {
				couleurs.put(en.getKey(), new Random().nextInt(10));
				votes.add(en.getKey());
				scoreboard.setLine(slines, "§" + couleurs.get(en.getKey()) + en.getKey() + " §e: §f" + en.getValue());
				slines++;
			}
			main.boards.put(p.getUniqueId(), scoreboard);
			}
		}
		
		
		if (timer == 0) {
			while(main.SondageList.size() != 1) {
				Integer i1 = main.SondageList.get(votes.get(0));
				Integer i2 = main.SondageList.get(votes.get(1));
				
				if (i1.compareTo(i2) > 0) {
					main.SondageList.remove(votes.get(1));
					votes.remove(1);
				} else if (i1.compareTo(i2) == 0) {
					int r = new Random().nextInt(2);
					if (r == 0) {
						main.SondageList.remove(votes.get(1));
						votes.remove(1);
					} else {
						main.SondageList.remove(votes.get(0));
						votes.remove(0);
					}
				} else {
					main.SondageList.remove(votes.get(0));
					votes.remove(0);
				}
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				String s = "";
				if (main.SondageList.get(votes.get(0)) != 1) s = "s";
				
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 1);
				main.sendTitle(p, "§" + couleurs.get(votes.get(0)) + votes.get(0) + " §aa gagné le sondage !", "§eAvec §c" + main.SondageList.get(votes.get(0)) + " §evote"+s+".", 20, 180, 20);
			}
			Bukkit.broadcastMessage(main.getPrefix() + "§8§l» §" + couleurs.get(votes.get(0)) + votes.get(0) + " §fa gagné le sondage avec §c" + main.SondageList.get(votes.get(0)) + " §fvotes !");
			main.SondageList.clear();
			votes.clear();
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (main.boards.containsKey(p.getUniqueId())) {
					main.boards.get(p.getUniqueId()).destroy();
				}
			}
		}
	
		for (Entry<UUID, ScoreboardSign> uuss : main.boards.entrySet()) {
			ScoreboardSign ss = uuss.getValue();
			int slines = 0;
			for(Entry<String, Integer> en : main.SondageList.entrySet()) {
				if (en.getValue() == 1) {
					ss.setLine(slines, "§" + couleurs.get(en.getKey()) + en.getKey() + " §e: §f" + en.getValue() + " vote");
				} else {
					ss.setLine(slines, "§" + couleurs.get(en.getKey()) + en.getKey() + " §e: §f" + en.getValue() + " votes");
				}
				slines++;
				}
			}
		if (timer == 1) {
			Index.sendActionBarForAllPlayers(main.getPrefix() + "§8§l» §e" + timer + " §fseconde avant la fin du sondage !");
		} else {
			Index.sendActionBarForAllPlayers(main.getPrefix() + "§8§l» §e" + timer + " §fsecondes avant la fin du sondage !");
		}
		timer--;
	}

}
