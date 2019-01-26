package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import model.Bestiary;

public class StorageManager {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final String STORAGE_PATH;
	private final String STORAGE_EXT = "json";
	private Bestiary bestiary = Bestiary.bestiary;
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
					
				} catch (IOException e) {
					logger.info("Load failure for "+fileEntry.getName());
					e.getStackTrace();
				}
	        }
	    }
	}
	
	private String readFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String fileContent = "";
		String currentLine = reader.readLine();
		while(currentLine != null) {
			fileContent += currentLine;
			currentLine = reader.readLine();
		}
		reader.close();
		return fileContent;
	}
}
