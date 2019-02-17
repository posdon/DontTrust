package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.CreatureListException;
import exception.JSONConverterException;
import model.Bestiary;
import model.Creature;

public class StorageManager {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final String STORAGE_PATH;
	private final String STORAGE_EXT = "json";
	private Bestiary bestiary = Bestiary.bestiary;
	private JSONConverter converter = JSONConverter.INSTANCE;
	private File folderCreature;

	
	public StorageManager(Bestiary bestiary, String storage_path) {
		this.bestiary = bestiary;
		this.STORAGE_PATH = storage_path;
		folderCreature = new File(STORAGE_PATH+"/creature");
	}
	
	public void readAllFiles() {
	    for (final File fileEntry : folderCreature.listFiles()) {
	        if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(STORAGE_EXT) && fileEntry.canRead()) {
	        	try {
					String fileContent = readFile(fileEntry);
					Creature creature = converter.stringToCreature(fileContent);
					bestiary.addCreature(creature);
				} catch (ParseException e) {
					logger.warn("Can't convert json into creature for "+fileEntry.getName());
				} catch (IOException e) {
					logger.error("Can't open "+fileEntry.getName());
					e.getStackTrace();
				} catch (CreatureListException e) {
					logger.warn(fileEntry.getName()+" already exist.");
				} catch (Exception e) {
					logger.warn("An creature name is blank.");
				}
	        }
	    }
	}
	
	private String readFile(File file) throws IOException {
		FileInputStream input = new FileInputStream(file);
		String fileContent = "";
		try {
		     fileContent = IOUtils.toString(input);
		     fileContent = fileContent.replaceAll("(\n|\t|\r)", "");
		} finally {
		    input.close();
		}
		return fileContent;
	}
	
	public void saveAllFiles() {
		List<File> allFiles = new ArrayList<File>();
		for(File fileEntry : folderCreature.listFiles()) {
			String fileName = fileEntry.getName().substring(0, fileEntry.getName().length()-STORAGE_EXT.length()-1);
			if(!Bestiary.bestiary.getAllCreaturesName().contains(fileName)) {
				fileEntry.delete();
			} else {
				allFiles.add(fileEntry);
			}
		}
		for(Creature creature : Bestiary.bestiary.getAllCreatures()) {
			File creatureFile = null;
			for(File currentFile : allFiles) {
				if(currentFile.getName().equals(creature.getName()+'.'+STORAGE_EXT)) creatureFile = currentFile;
			}
			if(creatureFile == null) creatureFile = new File(STORAGE_PATH+"/creature/"+creature.getName()+'.'+STORAGE_EXT);
			try {
				saveInFile(creature,creatureFile);
			} catch (FileNotFoundException e) {
				logger.error("Can't save the creature '"+creature.getName()+"' in file :"+creatureFile.getAbsolutePath());
			}
		}
		
	}
	
	public void saveInFile(Creature creature, File file) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);
		String creatureJSON;
		try {
			creatureJSON = converter.modelToJson(creature);
			writer.print(creatureJSON);
			writer.close();
		} catch (JSONConverterException e) {
			logger.error("Can't convert the creature '"+creature.getName()+"'");
		}
	}
}
