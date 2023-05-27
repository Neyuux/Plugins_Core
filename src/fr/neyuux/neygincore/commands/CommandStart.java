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

import fr.neyuux.neygincore.Core;

public class CommandStart implements CommandExecutor {

	private final Core main;
	
	public CommandStart(Core main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (Bukkit.getOnlinePlayers().size() != 0) {
			Player inventoryOpener = Bukkit.getOnlinePlayers().toArray(new Player[0])[0];
			Inventory inv = Bukkit.createInventory(null, InventoryType.BREWING, "startlamap");
			ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
			ItemMeta itm = it.getItemMeta();

			itm.setDisplayName("CONSOLE");
			if (sender instanceof Player) {
				Player player = (Player) sender;
				itm.setDisplayName(player.getName());
			}
			it.setItemMeta(itm);
			inv.addItem(it);
			inventoryOpener.openInventory(inv);
			inventoryOpener.closeInventory();

		} else sender.sendMessage(main.getPrefix() + "§8§l» §cUn joueur doit être connecté pour utiliser cette commande.");
		
		return true;
	}
}
