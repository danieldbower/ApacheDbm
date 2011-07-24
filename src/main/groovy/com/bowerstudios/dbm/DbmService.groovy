package com.bowerstudios.dbm

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.db.Database;
import com.sleepycat.db.DatabaseConfig;
import com.sleepycat.db.DatabaseType;

class DbmService {

	final Logger logger = LoggerFactory.getLogger(DbmService.class);
	
	void readDbm(String location){
		File dbmFile = openFile(location)
		Database dbm = openDbm(location)
		
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
