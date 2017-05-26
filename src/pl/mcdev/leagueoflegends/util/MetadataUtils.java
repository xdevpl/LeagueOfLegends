package pl.mcdev.leagueoflegends.util;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

import pl.mcdev.leagueoflegends.Main;

public final class MetadataUtils {
	private static final String PLUGIN_NAME = "lol";

	public void set(Metadatable m, String key, Object value) {
		m.setMetadata(PLUGIN_NAME + "_" + key, new FixedMetadataValue(Main.getInst(), value));
	}

	public void remove(Metadatable m, String key) {
		m.removeMetadata(PLUGIN_NAME + "_" + key, Main.getInst());
	}

	public boolean hasKey(Metadatable m, String key) {
		return m.getMetadata(PLUGIN_NAME + "_" + key).size() > 0;
	}

	public boolean hasKeys(Metadatable m, String... keys) {
		for (String key : keys)
			if (!hasKey(m, key))
				return false;
		return true;
	}

	public Object get(Metadatable m, String key) {
		return hasKey(m, key) ? m.getMetadata(PLUGIN_NAME + "_" + key).get(0).value() : null;
	}

	public void removeAll(Metadatable m, String... keys) {
		for (String k : keys)
			remove(m, k);
	}
}