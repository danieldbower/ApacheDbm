package com.bowerstudios.dbm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DbmServiceTest {

	DbmService service
	String testDbm = "src/test/resources/testdbm"
	
	final Logger logger = LoggerFactory.getLogger(DbmServiceTest.class);
	
	@Before
	public void setUp() throws Exception {
		service = new DbmService()
	}

	@Test
	public void testOpenFile() {
		assertNotNull(service.openFile(testDbm))
	}
	
	@Test
	public void testOpenCloseDbm(){
		def dbm = service.openDbm(testDbm)
		assertNotNull(dbm)
		service.closeDbm(dbm)
		assertTrue(true)
	}
	
	@Test
	public void readAllRecordsFromDbm(){
		Map data = service.readAllRecordsFromDbm(testDbm)
		assertNotNull(data)
		assertEquals(1, data.size())
		logger.info(data.toString())
	}

}
