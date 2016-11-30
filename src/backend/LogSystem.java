package backend;

import sun.rmi.runtime.Log;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A log system for the backend. This class can be instantiated only once because
 * singleton design pattern is used.
 */
public class LogSystem {
	/**
	 * The logger to record logs.
	 */
	private Logger logger = Logger.getLogger(LogSystem.class.getName());
	
	/**
	 * A file handler for the logger so that we can save the log's messages.
	 */
	private Handler fileHandler;

    /**
     * The only instance of this log system to be used.
     */
    private static final LogSystem INSTANCE = new LogSystem();

    /**
	 * The log system for the Photos in this Directory
	 * 
	 * @see Photo
	 */
	private LogSystem(){
		this.fileHandler = null;
		try {
			fileHandler = new FileHandler(this.getCurrentDirPath(), true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		logger.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		logger.addHandler(fileHandler);
	}

    /**
     * Return the only instace of LogSystem class.
     *
     * @return the only instance of this class.
     */
    public static LogSystem getInstance() {
        return INSTANCE;
    }
	
	/**
	 * Open and return a report of this log
	 * 
	 * @return a report of the this log
	 */
	public String getLogInfo(){
		String info = "";
		try{
			FileInputStream fstream = new FileInputStream(this.getCurrentDirPath());
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String s;
			while ((s = br.readLine()) != null)   {
				info += s + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * Add new Level.fine content with the given string message msg to this logger LogSystem.
	 *
	 * @param msg the message to be added to this logger.
	 */
	public void addToLog(String msg) {
        this.logger.log(Level.FINE, msg);
    }

	/**
	 * Find and return the data path of this current directory data path of the log file. This
	 * is done so the program can run on most operating systems.
	 *
	 * @return the data path of this current directory data path of the log file
	 */
	private String getCurrentDirPath() {
		String workSpaceDir = System.getProperty("user.dir");
		String dataPath = workSpaceDir + File.separator + "bin" ;
		File file = new File(dataPath, "data");
		file.mkdirs();
		return file.getAbsolutePath() + File.separator + "logFile.log";
	}


}
