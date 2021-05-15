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

public class CommandSpec implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!(sender instanceof Player)) return true;

		Player inventoryOpener = (Player)sender;
		Inventory inv = Bukkit.createInventory(null, InventoryType.BREWING, "commandespec");
		ItemStack itsender = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		ItemMeta itsm = itsender.getItemMeta();

		itsm.setDisplayName(inventoryOpener.getName());
		itsender.setItemMeta(itsm);
		inv.addItem(itsender);
		
		ItemStack arg0 = new ItemStack(Material.STRING, 1);
		ItemMeta it0m = arg0.getItemMeta();
		if (args.length == 0) it0m.setDisplayName("help");
		else if (args[0].equalsIgnoreCase("help")) it0m.setDisplayName("help");
		else if (args[0].equalsIgnoreCase("on")) it0m.setDisplayName("on");
		else if (args[0].equalsIgnoreCase("off")) it0m.setDisplayName("off");
		else if (args[0].equalsIgnoreCase("list")) it0m.setDisplayName("list");
		else it0m.setDisplayName("help");
		arg0.setItemMeta(it0m);
		inv.addItem(arg0);
		inventoryOpener.openInventory(inv);
		inventoryOpener.closeInventory();
		return true;
	}
}
