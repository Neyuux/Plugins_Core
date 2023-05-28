package fr.neyuux.neygincore;

import fr.neyuux.neygincore.commands.CommandTell;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class CoreListener implements Listener {
	
	
	final Location CoreSpawn = new Location(Bukkit.getWorld("Core"), -6.5, 49, -5.5, -89f, -9.4f);
	private final Core main;
	
	public CoreListener(Core main) {
		this.main = main;
	}
	
	

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent ev) {
		Player player = ev.getPlayer();

		for (Player p : Bukkit.getOnlinePlayers()) p.showPlayer(player);
		
		ev.setJoinMessage(null);
		int onlines = Bukkit.getOnlinePlayers().size();
		int maxonlines = Bukkit.getServer().getMaxPlayers();
		String joinmessage = "§a" + onlines;
		if (onlines >= (maxonlines / 4)) joinmessage = "§3" + onlines; 
		if (onlines >= (maxonlines / 3)) joinmessage = "§2" + onlines; 
		if (onlines >= (maxonlines / 2)) joinmessage = "§e" + onlines;
		if (onlines >= (maxonlines / 1.3)) joinmessage = "§c" + onlines;
		if (onlines >= maxonlines) joinmessage = "§4" + onlines;
		ev.setJoinMessage("§8[§a§l+§r§8] §e§o" + player.getName() + " §8(" + joinmessage + "§8/§c§l" + maxonlines + "§8)");
		
		if (onlines == 0) {
			PluginManager plm = Bukkit.getServer().getPluginManager();
			for (Plugin pl : plm.getPlugins()) {
				if (!pl.getName().equalsIgnoreCase("NeyGin_Core")) {
					plm.disablePlugin(pl);
				}
			}
		}
		
			switch(main.getCurrentGame()) {
			case NONE:
				player.setExp(0f);
				player.setLevel(0);
				player.setGameMode(GameMode.ADVENTURE);
				player.teleport(CoreSpawn);
				player.getInventory().clear();
				player.getInventory().setItem(4, getMainBoussole());
				player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
				break;
			case PVPKITS:
				Core.setPlayerTabList(player, "§e[§4§lP§b§lv§4§lP §9§lKits§r§e]§r" + "\n" + "§fBienvenue sur la map de §c§lNeyuux_" + "\n", "\n" + "§fMerci à §emini0x_ §fet §expbush §f les builders !");
				break;
			case LG:
				Core.setPlayerTabList(player, "§c§lLoups§e§l-§6§lGarous" + "\n" + "§fBienvenue sur la map de §c§lNeyuux_" + "\n", "\n" + "§fMerci à §emini0x_ §fet §expbush §f les builders !");
				break;
			case TOURNOI:
				Core.setPlayerTabList(player, "§6§lTournoi§r" + "\n" + "§fBienvenue sur la map de §c§lNeyuux_", "\n" + "§fMerci §eaux builders");
			default:
				break;
			
			}
		
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		event.setQuitMessage(null);
		int onlines = Bukkit.getOnlinePlayers().size() - 1;
		int maxonlines = Bukkit.getServer().getMaxPlayers();
		String joinmessage = "§a" + onlines;
		if (onlines >= (maxonlines / 4)) joinmessage = "§2" + onlines; 
		if (onlines >= (maxonlines / 3)) joinmessage = "§e" + onlines; 
		if (onlines >= (maxonlines / 2)) joinmessage = "§6" + onlines;
		if (onlines >= (maxonlines / 1.3)) joinmessage = "§c" + onlines;
		if (onlines >= maxonlines) joinmessage = "§4" + onlines;
		event.setQuitMessage("§8[§c§l-§r§8] §e§o" + player.getName() + " §8(" + joinmessage + "§8/§c§l" + maxonlines + "§8)");
		
		List<Entry<Player, Player>> toDelete = new ArrayList<>();
		for (Entry<Player, Player> en : CommandTell.lastMessages.entrySet()) {
			if (en.getKey().getName().equals(player.getName()) || en.getValue().getName().equals(player.getName()))
				toDelete.add(en);
		}
		for (Entry<Player, Player> del : toDelete)
			CommandTell.lastMessages.remove(del.getKey(), del.getValue());
		
	}
	
	
	
	@EventHandler
	public void onInteractBoussole(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		
		if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if (player.getInventory().getItemInHand().equals(getMainBoussole())) {
				Inventory inv = Bukkit.createInventory(null, 45, main.getPrefix());
				int[] slots = {19, 21, 23, 25};
				setInvCoin(inv, 0, (byte)1);
				setInvCoin(inv, 8, (byte)2);
				setInvCoin(inv, 36, (byte)3);
				setInvCoin(inv, 44, (byte)4);
				
				int i = 0;
				for (CurrentGame cg : CurrentGame.values())
					if (!cg.equals(CurrentGame.NONE)) {
						inv.setItem(slots[i], getGameItem(cg));
						i++;
					}
				player.openInventory(inv);
			}
		}
		
	}
	



	@EventHandler
	public void onInvBoussole(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();
		
		if (current == null) return;
		
		if (inv.getName().equalsIgnoreCase(main.getPrefix())) {
			event.setCancelled(true);
			if (!current.getType().equals(Material.STAINED_GLASS_PANE) && !player.isOp()) {
				player.sendMessage(main.getPrefix() + "§8§l» §cTu dois être op pour lancer un jeu !");
				return;
			}
			
			if (CurrentGame.getByMaterial(current.getType()) != null)
				main.setGame(CurrentGame.getByMaterial(current.getType()));
		}
		
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (main.isCurrentGame(CurrentGame.NONE))
			if (!player.getGameMode().equals(GameMode.CREATIVE)) {
				event.setCancelled(true);
				player.updateInventory();
			}
	}
	
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (main.isCurrentGame(CurrentGame.NONE))
			if (!player.getGameMode().equals(GameMode.CREATIVE)) {
				event.setCancelled(true);
				player.updateInventory();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBurnBlock(BlockBurnEvent e) {
		if (!main.isCurrentGame(CurrentGame.PVPKITS)) e.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
    public void onWeatherChange(WeatherChangeEvent event) {
        if(event.toWeatherState())
            event.setCancelled(true);
    }
  
    @EventHandler(priority=EventPriority.LOWEST)
    public void onThunderChange(ThunderChangeEvent event) {
        if(event.toThunderState())
            event.setCancelled(true);
    }
	
	
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		if (main.isCurrentGame(CurrentGame.NONE)) ev.setCancelled(true);
	}
	
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event){
	    if (event.getCause().equals(IgniteCause.SPREAD)) event.setCancelled(true);
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if (main.isCurrentGame(CurrentGame.NONE)) ev.setCancelled(true);
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent ev) {
		Player player = ev.getPlayer();
		String cmd = ev.getMessage();
		
		if (cmd.toLowerCase().startsWith("/kill") || cmd.toLowerCase().startsWith("/gamemode") || cmd.toLowerCase().startsWith("/kick") || cmd.toLowerCase().startsWith("/ban") || cmd.toLowerCase().startsWith("/clear") || cmd.toLowerCase().startsWith("/effect") || cmd.toLowerCase().startsWith("/give") || cmd.toLowerCase().startsWith("/tp ") || cmd.toLowerCase().startsWith("/pardon")) {
			if (player.isOp())	Bukkit.broadcastMessage(main.getPrefix() + "§8§l§ §b" + player.getName() + "§e a effectu§ la commande : §c" + cmd);
		}
		
		if (cmd.toLowerCase().startsWith("/op") || cmd.toLowerCase().startsWith("/deop") || cmd.toLowerCase().startsWith("/gamerule") || cmd.toLowerCase().startsWith("/restart") || cmd.toLowerCase().startsWith("/stop") || cmd.toLowerCase().startsWith("/scoreboard")) {
			if (!player.getUniqueId().toString().equals("0234db8c-e6e5-45e5-8709-ea079fa575bb")) {
				ev.setCancelled(true);
				player.sendMessage(main.getPrefix() + "§8§l» §cT ki ?");
			}
		}
		
		Player moi = null;
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getUniqueId().toString().equals("0234db8c-e6e5-45e5-8709-ea079fa575bb"))
				moi = p;
		if (moi != null) {
			if (cmd.toLowerCase().startsWith("/kill neyuux_") || cmd.toLowerCase().startsWith("/kick neyuux_") || cmd.toLowerCase().startsWith("/ban neyuux_") || cmd.toLowerCase().startsWith("/clear neyuux_")) {
				String firstcmd = cmd.toLowerCase().split(" neyuux_")[0].replace("/", "");
				Bukkit.broadcastMessage(main.getPrefix() + "§8§l§ §bLE KARMA §ea effectué la commande : §c/" + firstcmd + " " + player.getName() + "§e. (Quel fdp ce " + player.getName() + ")");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), firstcmd + " " + player.getName());
				ev.setCancelled(true);
			}
		}
		
		if (cmd.toLowerCase().startsWith("/rl") || cmd.toLowerCase().startsWith("/reload") && player.isOp())
			for (Player p : Bukkit.getOnlinePlayers()) {
				ScoreboardSign ss = new ScoreboardSign(p, "reset");
				ss.create();
				ss.setLine(0, "reset15");
				ss.setLine(1, "reset14");
				ss.setLine(2, "reset13");
				ss.setLine(3, "reset12");
				ss.setLine(4, "reset11");
				ss.setLine(5, "reset10");
				ss.setLine(6, "reset9");
				ss.setLine(7, "reset8");
				ss.setLine(8, "reset7");
				ss.setLine(9, "reset6");
				ss.setLine(10, "reset5");
				ss.setLine(11, "reset4");
				ss.setLine(12, "reset3");
				ss.setLine(13, "reset2");
				ss.setLine(14, "reset1");
				ss.destroy();
				main.setGame(CurrentGame.NONE);
				System.setProperty("RELOAD", "TRUE");
			}
	}



	private ItemStack getMainBoussole() {
		ItemStack it = new ItemStack(Material.COMPASS);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName("§a§lJ§b§lE§a§lU§b§lX");
		itm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itm.addEnchant(Enchantment.DURABILITY, 1, true);
		itm.setLore(Collections.singletonList("§7>> §bClique droit §apour ouvrir le menu"));
		it.setItemMeta(itm);
		
		return it;
	}
	
	
	private void setInvCoin(Inventory inv, int slot, Byte sens) {
		ItemStack verre = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 12);
		if (sens == 1) {
		inv.setItem((slot), verre);
		inv.setItem((slot + 9), verre);
		inv.setItem((slot + 1), verre);
		} else if (sens == 2) {
			inv.setItem((slot), verre);
			inv.setItem((slot + 9), verre);
			inv.setItem((slot - 1), verre);
		} else if (sens == 3) {
			inv.setItem((slot), verre);
			inv.setItem((slot - 9), verre);
			inv.setItem((slot + 1), verre);
		} else if (sens == 4) {
			inv.setItem((slot), verre);
			inv.setItem((slot - 9), verre);
			inv.setItem((slot - 1), verre);
		}
	}
	
	
	private static ItemStack getGameItem(CurrentGame cg) {
		ItemStack it = new ItemStack(cg.getGamesInvType());
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName(cg.getPluginPrefix());
		List<String> lore = new ArrayList<>();
		String smaxj = "" + cg.getMaxPlayers();
		if (cg.getMaxPlayers() == Integer.MAX_VALUE) smaxj = "INFINI";
		StringBuilder line = new StringBuilder("§7");
		int i = 0;
		for(String bout : cg.getDescription().split(" ")){
			line.append(bout).append(" ");
			if(i == 8){
				lore.add(line.toString());
				line = new StringBuilder("§7");
				i = 0;
			}
			i++;
		}
		if(!line.toString().equals("§7")) lore.add(line.toString());
		lore.add("§fNombre de joueurs §7: §ede §c" + cg.getMinPlayers() + "§e à §c" + smaxj);
		lore.add("");
		lore.add("§fCréateur §7: §e" + cg.getCredit());
		lore.add("§fDéveloppeur §7: §e" + cg.getCreator());
		lore.add("");
		lore.add("§e>> §eClique droit §bpour lancer le jeu !");
		itm.setLore(lore);
		it.setItemMeta(itm);
	
		return it;
	}
	
}
