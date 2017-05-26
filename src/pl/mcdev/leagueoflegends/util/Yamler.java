package pl.mcdev.leagueoflegends.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import pl.mcdev.leagueoflegends.Main;


public class Yamler extends YamlConfiguration {
	
	private File file;
	private boolean nf;
	
	public Yamler(File file){
		this(file, false);
	}
	
	public Yamler(File file, boolean newFile){
		super();
		nf = newFile;
		this.file = file;
		check();
		try {
			super.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(File file){
		check();
		try {
			super.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(){
		check();
		try {
			super.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void check() {
		if(!file.getParentFile().exists()) file.getParentFile().mkdir();
	    if(!file.exists()) {
	    	if(nf) try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			else IOUtils.copy(Main.getInst().getResource(file.getName()), file);
	    }
	}

	public void clear() {
		file.delete();
		try {
			file.createNewFile();
			super.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<String> getSectionKeys2(String path){
		Collection<String> list = new ArrayList<>();
		path += ".";
		for(String key : map.keySet()) if(key.startsWith(path)) list.add(key.substring(path.length()));
		return list;
	}
}
