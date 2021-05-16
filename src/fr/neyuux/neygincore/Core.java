package fr.neyuux.neygincore;

import fr.neyuux.neygincore.commands.*;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

public class Core extends JavaPlugin {
	
	
	private CurrentGame cgame;
	public static final String prefix = "�4�lNey�6G�ei�2n�4�l_";
	
	
	@Override
	public void onEnable() {
		System.setProperty("RELOAD", "TRUE");
		
		if (!System.getProperties().containsKey("RELOAD")) {
			Properties prop = new Properties(System.getProperties());
			prop.put("TOURNAMENTCONNEXION", "FALSE");
		}
		
		System.out.println("NeyGin_Core Enabling");
		setCurrentGame(CurrentGame.NONE);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new CoreListener(this), this);
		
		for (Plugin p : pm.getPlugins()) {
			for (CurrentGame cg : CurrentGame.values()) {
				if (p.isEnabled() && p.getName().equals(cg.getPluginName()))
					pm.disablePlugin(p);
			}
		}
		
		getCommand("game").setExecutor(new CommandGame(this));
		getCommand("sondage").setExecutor(new CommandSondage(this));
		getCommand("end").setExecutor(new CommandEnd(this));
		getCommand("broadcast").setExecutor(new CommandBroadcast(this));
		getCommand("spec").setExecutor(new CommandSpec());
		getCommand("start").setExecutor(new CommandStart(this));
		getCommand("tell").setExecutor(new CommandTell(this));
		getCommand("respond").setExecutor(new CommandRespond(this));
		getCommand("tournamentconnexion").setExecutor(new CommandTournamentConnexion(this));
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		System.out.println("NeyGin_Core Disabling");
		
		super.onDisable();
	}
	
	

	public void setCurrentGame(CurrentGame cgame) {
		
		this.cgame = cgame;
		
	}
	
	public boolean isCurrentGame(CurrentGame cgame) {
		return this.cgame == cgame;
	}
	
	public CurrentGame getCurrentGame() {
		return this.cgame;
	}
	
	
	
	public String getPrefix() {
		return prefix;
	}
	
	
	
	public void setGame(CurrentGame cg) {
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
		}
		if (cg.equals(CurrentGame.NONE)) {
			PluginManager plm = Bukkit.getServer().getPluginManager();
			for (Plugin p : plm.getPlugins()) {
				for (CurrentGame c : CurrentGame.values()) {
					if (p.isEnabled() && p.getName().equals(c.getPluginName()) )
						plm.disablePlugin(p);
				}
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.setGameMode(GameMode.ADVENTURE);
				p.teleport(new Location(Bukkit.getWorld("Core"), -565, 23.2, 850));
				p.getInventory().clear();
				setPlayerTabList(p, "", "");
				ItemStack it = new ItemStack(Material.COMPASS);
				ItemMeta itm = it.getItemMeta();
				itm.setDisplayName("�a�lJ�b�lE�a�lU�b�lX");
				itm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				itm.addEnchant(Enchantment.DURABILITY, 1, true);
				itm.setLore(Collections.singletonList("�7>> �bClique droit �apour ouvrir le menu"));
				it.setItemMeta(itm);
				p.getInventory().setItem(4, it);
			}
			setCurrentGame(CurrentGame.NONE);
		} else {
			if (cg.getWorldName() != null) Bukkit.getServer().createWorld(new WorldCreator(cg.getWorldName()));
			PluginManager plm = Bukkit.getServer().getPluginManager();
			for (Plugin pl : plm.getPlugins()) {
				if (!pl.getName().equalsIgnoreCase("NeyGin_Core")) {
					plm.disablePlugin(pl);
				}
			}
			System.setProperty("RELOAD", "FALSE");
			System.setProperty("TOURNAMENTCONNEXION", "FALSE");
			Bukkit.getServer().getPluginManager().enablePlugin(Bukkit.getServer().getPluginManager().getPlugin(cg.getPluginName()));
			setCurrentGame(cg);
		
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.getInventory().remove(Material.COMPASS);
				if (cg.equals(CurrentGame.PVPKITS)) Core.setPlayerTabList(p, "�e[�4�lP�b�lv�4�lP �9�lKits�r�e]�r" + "\n" + "�fBienvenue sur la map de �c�lNeyuux_" + "\n", "\n" + "�fMerci � �emini0x_ �fet �expbush �f les builders !");
				if (cg.equals(CurrentGame.LG)) Core.setPlayerTabList(p, "�c�lLoups�e�l-�6�lGarous" + "\n" + "�fBienvenue sur la map de �c�lNeyuux_" + "\n", "\n" + "�fMerci � �emini0x_ �fet �expbush �f les builders !");
				if (cg.equals(CurrentGame.TOURNOI)) Core.setPlayerTabList(p, "�6�lTournoi�r" + "\n" + "�fBienvenue sur la map de �c�lNeyuux_", "\n" + "�fMerci �eaux builders");

				try {
					if (!Double.isNaN(cg.getSpawnX()))
						p.teleport(new Location(Bukkit.getWorld(cg.getWorldName()), cg.getSpawnX(), cg.getSpawnY(), cg.getSpawnZ()));
				} catch (NullPointerException ignored) {}
			}
		}
	}



	public static void setPlayerTabList(Player player,String header, String footer) {
		IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
		try {
			Field field = packet.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(packet, tabFoot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sendPacket(player, packet);
		}
	}



	private static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server."
					+ Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		try {
			Object chatTitle = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + title + "\"}");
			Constructor<?> titleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object packet = titleConstructor.newInstance(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
					fadeInTime, showTime, fadeOutTime);

			Object chatsTitle = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + subtitle + "\"}");
			Constructor<?> timingTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object timingPacket = timingTitleConstructor.newInstance(
					Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
					fadeInTime, showTime, fadeOutTime);

			sendPacket(player, packet);
			sendPacket(player, timingPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void sendActionBar(Player p, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		try {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
		} catch (NullPointerException e) {e.printStackTrace();}
	}

}
