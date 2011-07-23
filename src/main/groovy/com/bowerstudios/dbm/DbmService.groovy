package com.bowerstudios.dbm

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;


class DbmService {

	final Logger logger = LoggerFactory.getLogger(DbmService.class);
	
	void readDbm(String location){
		File dbmFile = openFile(location)
		
		
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
	protected Environment openDbm(File dbmFile){
		Environment dbmEnv
		EnvironmentConfig dbmEnvConfig
		
		if(dbmFile){
			dbmEnvConfig = new EnvironmentConfig()
			dbmEnvConfig.setAllowCreate(true);
						
			dbmEnv = new Environment(dbmFile, dbmEnvConfig)
			return dbmEnv
		}else{
			return null
		}
		
	}
	
	/**
	 * Close a Dbm Database
	 */
	protected void closeDbm(Environment dbmEnv){
		if(dbmEnv){
			dbmEnv.close()
		}
	}
}
