package com.bowerstudios.dbm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DbmServiceTest {

	DbmService service
	
	final Logger logger = LoggerFactory.getLogger(DbmServiceTest.class);
	
	@Before
	public void setUp() throws Exception {
		service = new DbmService()
	}

	@Test
	public void testOpenFile() {
		assertNotNull(service.openFile("src/test/resources/testdbm"))
	}
	
	

}
