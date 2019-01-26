package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.CreatureListException;
import model.Bestiary;
import model.Creature;

public class StorageManager {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final String STORAGE_PATH;
	private final String STORAGE_EXT = "json";
	private Bestiary bestiary = Bestiary.bestiary;
	private JSONConverter converter = JSONConverter.INSTANCE;
	private File folder;

	
	public StorageManager(Bestiary bestiary, String storage_path) {
		this.bestiary = bestiary;
		this.STORAGE_PATH = storage_path;
		folder = new File(STORAGE_PATH);
	}
	
	public void readAllFiles() {
	    for (final File fileEntry : folder.listFiles()) {
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
}
