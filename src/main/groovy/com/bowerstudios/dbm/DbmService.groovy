package com.bowerstudios.dbm

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.db.Cursor;
import com.sleepycat.db.Database;
import com.sleepycat.db.DatabaseConfig;
import com.sleepycat.db.DatabaseEntry;
import com.sleepycat.db.DatabaseException;
import com.sleepycat.db.DatabaseType;
import com.sleepycat.db.LockMode;
import com.sleepycat.db.OperationStatus;

class DbmService {

	final Logger logger = LoggerFactory.getLogger(DbmService.class);
	
	Map readAllRecordsFromDbm(String location){
		Map result = [:]
			
		withDbmCursor(location){ Cursor cursor ->
			// Cursors need a pair of DatabaseEntry objects to operate. These hold
			// the key and data found at any given position in the database.
			DatabaseEntry foundKey = new DatabaseEntry();
			DatabaseEntry foundData = new DatabaseEntry();
			
			// To iterate, just call getNext() until the last database record has
			// been read. All cursor operations return an OperationStatus, so just
			// read until we no longer see OperationStatus.SUCCESS
			while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
					String keyString = new String(foundKey.getData(), "UTF-8")
					String dataString = new String(foundData.getData(), "UTF-8")
					result.put(keyString, dataString)
			}
		}
		
		return result
	}
	
	
	
	protected void withDbmCursor(String location, Closure closure){
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
	
	protected File openFile(String location){
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
