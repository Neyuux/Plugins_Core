package fr.neyuux.neygincore;

import com.google.common.collect.ImmutableList;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CurrentGame {

	NONE(Collections.emptyList(), Core.prefix, null, null, "Neyuux_", "Neyuux_", 0, 0, "Core", -6.5, 49, -5.5),
	PVPKITS(Collections.singletonList("PvPKits"), "§b§lPvPKits", Material.MUSHROOM_SOUP, "§7Choisis ton kit avec stratégie en compagnie de tes teammates puis va combattre les autres équipes !", "Neyuux_", "Neyuux_", 2, Integer.MAX_VALUE, "PvPKits", -6.096, 5.1, -2.486),
	LG(ImmutableList.of("LG", "SmartInvs"), "§c§lLoups§e§l-§6§lGarous", Material.MONSTER_EGG, "§7Mode de jeu stratégique, obtient ton rôle, joue et gagne avec ton camp. Sauras-tu rentrer dans la tête de tes amis ?", "Neyuux_", "Philippe des Pallières et Hervé Marly", 5, 26, "LG", 130, 16.5, 380),
	LG_UHC(Arrays.asList("Privates-AddonLG", "WereWolfPlugin"), "§f[§bLG UHC§f]", Material.COBBLESTONE, "§7UHC mais avec des rôles tirés du jeu les Loups-Garous de Thericelieux (c'est bien dév tkt)", "Ph1lou", "Ph1lou", 2, 30),
	TOURNOI(Collections.singletonList("Tournoi"), "§a§lTournoi", Material.GOLD_HELMET, "§7Tournoi en 1 contre 1 avec comme thème la coupe du monde.", "Neyuux_", "Neyuux_", 2, 64, "Tournoi", -579, 84, 336),
	UHC(Collections.singletonList("UHC"), "§e§lUHC", Material.GOLDEN_APPLE, "§7C'est un UHC tu connais.", "Neyuux_", "Aucun", 2, Integer.MAX_VALUE);

	CurrentGame(List<String> pluginNames, String pluginPrefix, Material gamesInvType, String desc, String creator, String credit, int minPlayers, int maxPlayers, String worldName, double spawnX, double spawnY, double spawnZ) {
		this.pluginNames = pluginNames;
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

	CurrentGame(List<String> pluginNames, String pluginPrefix, Material gamesInvType, String desc, String creator, String credit, int minPlayers, int maxPlayers) {
		this.pluginNames = pluginNames;
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

	private final List<String> pluginNames;
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

	private boolean isDetected = false;


	public List<String> getPluginNames() {
		return pluginNames;
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

	public static CurrentGame getByName(String name) {
		for (CurrentGame cg : CurrentGame.values())
			if (cg.name().equalsIgnoreCase(name))
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

	public boolean isDetected() {
		return isDetected;
	}

	public void setDetected(boolean detected) {
		isDetected = detected;
	}
}
