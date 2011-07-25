package com.bowerstudios.apachedbm

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.db.DatabaseEntry;

class ApacheDbmUser {
	
	String username
	String encodedPassword
	List<String> groups
	String comments
	
	final Logger logger = LoggerFactory.getLogger(ApacheDbmManager.class);
	
	public ApacheDbmUser(){
		
	}
	
	/**
	* Create an ApacheDbmUser from a (Dbm Key or Username) and a Dbm Value
	*/
	public ApacheDbmUser(String username, DatabaseEntry key, DatabaseEntry value){
		if(!username && key){
			this.username = new String(key.getData(), "UTF-8")
		}else if(!username && !key){
			throw new IllegalArgumentException("Either username or key must be provided");
		}else{
			this.username = username
			String userDataString =  new String(value.getData(), "UTF-8")
			List<String> userData =userDataString.tokenize(':')
			encodedPassword = userData[0]
			groups = (userData[1])?.tokenize(',')
			comments = userData[2]
		}
	}
	
	public String toString(){
		return "[username: $username, encodedPassword: $encodedPassword, groups: $groups?.toString(), comments: $comments"
	}
}
