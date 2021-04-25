package fr.neyuux.neygincore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.neyuux.neygincore.Index;

public class CommandSpec implements CommandExecutor {

	private Index main;
	
	public CommandSpec(Index main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (Bukkit.getOnlinePlayers().size() == 0) {
			sender.sendMessage(main.getPrefix() + "§8§l» §cUn joueur doit être connecté pour utiliser cette commande.");
		}
		
		Player inventoryOpener = null;
		while (inventoryOpener == null) {
			for (Player p : Bukkit.getOnlinePlayers()) inventoryOpener = p;
		}
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.BREWING, "commandespec");
		
		ItemStack itsender = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		ItemMeta itsm = itsender.getItemMeta();
		itsm.setDisplayName("CONSOLE");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			itsm.setDisplayName(player.getName());
		} else {
			sender.sendMessage(main.getPrefix() + "§8§l» §cVous devez être un joueur pour faire cette commande.");
		}
		itsender.setItemMeta(itsm);
		inv.addItem(itsender);
		
		ItemStack arg0 = new ItemStack(Material.STRING, 1);
		ItemMeta it0m = arg0.getItemMeta();
		if (args.length == 0) {
			it0m.setDisplayName("help");
		} else {
			if (args[0].equalsIgnoreCase("help")) {
				it0m.setDisplayName("help");
			} else if (args[0].equalsIgnoreCase("on")) {
				it0m.setDisplayName("on");
			} else if (args[0].equalsIgnoreCase("off")) {
				it0m.setDisplayName("off");
			} else if (args[0].equalsIgnoreCase("list")) {
				it0m.setDisplayName("list");
			} else {
				it0m.setDisplayName("help");
			}
		}
		arg0.setItemMeta(it0m);
		inv.addItem(arg0);
		inventoryOpener.closeInventory();
		inventoryOpener.openInventory(inv);
		inventoryOpener.closeInventory();
		
		return true;
	}
}
