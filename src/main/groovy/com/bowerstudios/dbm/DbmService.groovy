package com.bowerstudios.dbm

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class DbmService {

	final Logger logger = LoggerFactory.getLogger(DbmService.class);
	
	void readDbm(String location){
		File dbm = openFile(location)
	}
	
	File openFile(String location){
		File file = new File(location)
		if(file.exists() && file.canRead()){
			return file
		}else{
		   logger.error("File $location not found")
		   return null
		}
	}
}
