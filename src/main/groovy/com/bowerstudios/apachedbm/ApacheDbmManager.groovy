package com.bowerstudios.apachedbm

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bowerstudios.apachedbm.ApacheDbmManager;
import com.sleepycat.db.Cursor;
import com.sleepycat.db.Database;
import com.sleepycat.db.DatabaseConfig;
import com.sleepycat.db.DatabaseEntry;
import com.sleepycat.db.DatabaseException;
import com.sleepycat.db.DatabaseType;
import com.sleepycat.db.LockMode;
import com.sleepycat.db.OperationStatus;


/**
 * Manage a DBM file that is used to authenticate with the Apache Web Server
 * @author Daniel Bower
 *
 */
class ApacheDbmManager {

	final Logger logger = LoggerFactory.getLogger(ApacheDbmManager.class);
	
	/**
	 * Read all the user records in the file at location, and return them as a list
	 */
	List<ApacheDbmUser> readAllRecordsFromDbm(String location){
		List result = new ArrayList<ApacheDbmUser>()
			
		withDbmCursor(location){ Cursor cursor ->
			// Cursors need a pair of DatabaseEntry objects to operate. These hold
			// the key and data found at any given position in the database.
			DatabaseEntry key = new DatabaseEntry();
			DatabaseEntry value = new DatabaseEntry();
			
			// To iterate, just call getNext() until the last database record has
			// been read. All cursor operations return an OperationStatus, so just
			// read until we no longer see OperationStatus.SUCCESS
			while (cursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				result.add(new ApacheDbmUser(null, key, value))
			}
		}
		
		return result
	}
	
	/**
	 * Get a user record with username  from the file at location
	 */
	ApacheDbmUser getUserRecordFromDbm(String location, String username){
		ApacheDbmUser result = null
		
		Database dbm = openDbm(location)
		try {
			DatabaseEntry userDataEntry = new DatabaseEntry()
			OperationStatus opStatus = dbm.get(null, 
				new DatabaseEntry(username.getBytes("UTF-8")), 
				userDataEntry , LockMode.DEFAULT)
			
			if(opStatus == OperationStatus.SUCCESS){
				result = new ApacheDbmUser(username, null, userDataEntry)
			}else{
				logger.debug("User username not found")
			}
			
		} catch (DatabaseException dbe){
			logger.error("Error in close: " + dbe.toString());
			
		} finally {
			if(dbm){
				closeDbm(dbm)
			}
		}
		
		return result
	}
	
	/**
	 * Utility method for working with a dbm cursor
	 */
	private void withDbmCursor(String location, Closure closure){
		Database dbm = openDbm(location)
		Cursor cursor
		try {
			cursor = dbm.openCursor(null, null);
			closure(cursor)
		} catch (DatabaseException dbe){
			logger.error("Error in close: " + dbe.toString());
		} finally {
			if (cursor) {
				cursor.close();
			}
			if(dbm){
				closeDbm(dbm)
			}
		}
	}
	
	private File openFile(String location){
		File file = new File(location)
		if(file.exists() && file.canRead()){
			return file
		}else{
		   logger.error("File $location not found")
		   return null
		}
	}
	
	/**
	 * Open a Dbm Database
	 */
	protected Database openDbm(String dbmFile){
		if(!openFile(dbmFile)){
			return
		}
		
		Database dbm
		DatabaseConfig dbmConfig
		
		if(dbmFile){
			dbmConfig = new DatabaseConfig()
			dbmConfig.setAllowCreate(true)
			dbmConfig.setType(DatabaseType.HASH)
			dbm = new Database(dbmFile, null, dbmConfig)
			return dbm
		}else{
			return null
		}
	}
	
	/**
	 * Close a Dbm Database
	 */
	protected void closeDbm(Database dbm){
		if(dbm){
			dbm.close()
		}
	}
}
