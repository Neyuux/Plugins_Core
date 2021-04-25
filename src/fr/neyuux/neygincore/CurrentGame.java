package fr.neyuux.neygincore;

public enum CurrentGame {
	
	NONE(""), PVPKITS("PvPKits"), LG("LG"), TOURNOI("Tournoi"), UHC("UHC");
	
	CurrentGame(String pluginName) {
		this.pluginName = pluginName;
	}
	
	private final String pluginName;

	
	public String getPluginName() {
		return pluginName;
	}
}
