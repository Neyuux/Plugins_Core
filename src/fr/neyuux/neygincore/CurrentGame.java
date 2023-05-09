package fr.neyuux.neygincore;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public enum CurrentGame {

	NONE("None", Core.prefix, null, null, "Neyuux_", "Neyuux_", 0, 0, "Core", -6.5, 49, -5.5),
	PVPKITS("PvPKits", "§e[§4§lP§b§lv§4§lP §9§lKits§r§e]", Material.MUSHROOM_SOUP, "§7Choisis ton kit avec strat§gie en compagnie de tes teammates puis va combattre les autres §quipes !", "Neyuux_", "Neyuux_", 2, Integer.MAX_VALUE, "PvPKits", -6.096, 5.1, -2.486),
	LG("LG", "§c§lLoups§e§l-§6§lGarous", Material.MONSTER_EGG, "§7Mode de jeu strat§gique, obtient ton r§le, joue et gagne avec ton camp. Sauras-tu rentrer dans la t§te de tes amis ?", "Neyuux_", "Philippe des Palli§res et Herv§ Marly", 5, 26, "LG", 130, 16.5, 380),
	TOURNOI("Tournoi", "§6§lTournoi", Material.GOLD_HELMET, "§7Tournoi en 1 contre 1 avec comme th§me la coupe du monde.", "Neyuux_", "Neyuux_", 2, 64, "Tournoi", -579, 84, 336),
	UHC("UHC", "§e§lUHC", Material.GOLDEN_APPLE, "§7C'est un UHC tu connais.", "Neyuux_", "Aucun", 2, Integer.MAX_VALUE);

	CurrentGame(String pluginName, String pluginPrefix, Material gamesInvType, String desc, String creator, String credit, int minPlayers, int maxPlayers, String worldName, double spawnX, double spawnY, double spawnZ) {
		this.pluginName = pluginName;
		this.pluginPrefix = pluginPrefix;
		this.worldName = worldName;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.spawnZ = spawnZ;
		this.gamesInvType = gamesInvType;
		this.desc = desc;
		this.creator = creator;
		this.credit = credit;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	CurrentGame(String pluginName, String pluginPrefix, Material gamesInvType, String desc, String creator, String credit, int minPlayers, int maxPlayers) {
		this.pluginName = pluginName;
		this.pluginPrefix = pluginPrefix;
		this.worldName = null;
		this.spawnX = Double.NaN;
		this.spawnY = Double.NaN;
		this.spawnZ = Double.NaN;
		this.gamesInvType = gamesInvType;
		this.desc = desc;
		this.creator = creator;
		this.credit = credit;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	private final String pluginName;
	private final String pluginPrefix;
	private final String worldName;
	private final double spawnX;
	private final double spawnY;
	private final double spawnZ;
	private final Material gamesInvType;
	private final String desc;
	private final String creator;
	private final String credit;
	private final int minPlayers;
	private final int maxPlayers;


	public String getPluginName() {
		return pluginName;
	}

	public String getPluginPrefix() {
		return pluginPrefix;
	}

	public String getWorldName() {
		return worldName;
	}

	public double getSpawnX() {
		return spawnX;
	}

	public double getSpawnY() {
		return spawnY;
	}

	public double getSpawnZ() {
		return spawnZ;
	}

	public Material getGamesInvType() {
		return gamesInvType;
	}

	public String getDescription() {
		return desc;
	}

	public String getCreator() {
		return creator;
	}

	public String getCredit() {
		return credit;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public static CurrentGame getByPluginName(String name) {
		for (CurrentGame cg : CurrentGame.values())
			if (cg.getPluginName().equalsIgnoreCase(name))
				return cg;
		return null;
	}

	public static CurrentGame getByMaterial(Material mat) {
		if (mat == null) return NONE;
		for (CurrentGame cg : CurrentGame.values())
			if (cg.getGamesInvType() != null && cg.getGamesInvType().equals(mat))
				return cg;
		return null;
	}
}
