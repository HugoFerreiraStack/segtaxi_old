package br.com.seg.econotaxi.view.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
/**
 * 
 * @author https://crunchify.com
 */
public class LogFileTailer implements Runnable {
 
	private boolean debug = false;
 
	private int crunchifyRunEveryNSeconds = 2000;
	private long lastKnownPosition = 0;
	private boolean shouldIRun = true;
	private File crunchifyFile = null;
	private static int crunchifyCounter = 0;
	private StringBuilder log;
 
	public LogFileTailer(String myFile, int myInterval) {
		log = new StringBuilder();
		crunchifyFile = new File(myFile);
		this.crunchifyRunEveryNSeconds = myInterval;
		run();
	}
 
	public void stopRunning() {
		shouldIRun = false;
	}
 
	public void run() {
		try {
			//while (shouldIRun) {
				//Thread.sleep(crunchifyRunEveryNSeconds);
				//long fileLength = crunchifyFile.length();
				//if (fileLength > lastKnownPosition) {
 
					// Reading and writing file
					RandomAccessFile readWriteFileAccess = new RandomAccessFile(crunchifyFile, "rw");
					readWriteFileAccess.seek(lastKnownPosition);
					String crunchifyLine = null;
					while ((crunchifyLine = readWriteFileAccess.readLine()) != null) {
						log.append(crunchifyLine + "\n");
						//this.printLine(crunchifyLine);
						crunchifyCounter++;
					}
					lastKnownPosition = readWriteFileAccess.getFilePointer();
					readWriteFileAccess.close();
				//} else {
					//if (debug)
						//log.append("Hmm.. Couldn't found new line after line # " + crunchifyCounter);
				//}
			//}
		} catch (Exception e) {
			stopRunning();
		}
		if (debug)
			log.append("Exit the program...");
	}
 
	public static void main(String argv[]) {
 
		ExecutorService crunchifyExecutor = Executors.newFixedThreadPool(4);
 
		// Replace username with your real value
		// For windows provide different path like: c:\\temp\\crunchify.log
		String filePath = "C:\\Petrim\\Desenvolvimento\\Servers\\wildfly-9.0.2.Final\\standalone\\log\\server.log";
		LogFileTailer crunchify_tailF = new LogFileTailer(filePath, 2000);
 
		// Start running log file tailer on crunchify.log file
		crunchifyExecutor.execute(crunchify_tailF);
 
		// Start pumping data to file crunchify.log file
		//appendData(filePath, true, 5000);
 
	}

	public StringBuilder getLog() {
		return log;
	}

	public void setLog(StringBuilder log) {
		this.log = log;
	}
	
}