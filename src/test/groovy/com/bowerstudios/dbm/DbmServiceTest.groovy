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
		List data = service.readAllRecordsFromDbm(testDbm)
		assertNotNull(data)
		assertEquals(4, data.size())
		logger.info(data.toString())
	}
	
	@Test
	public void getUserOnlyFromDbm(){
		String username = 'justUser'
		Map user = service.getUserRecordFromDbm(testDbm, username)
		assertNotNull(user)
		logger.debug(user.toString())
		
		assertEquals(username, user['username'])
		assertNotNull(user['encodedPassword'])
		assertNull(user['groups'])
		assertNull(user['comments'])
	}
	
	@Test
	public void getUserAnd1GroupFromDbm(){
		String username = 'userAnd1Group'
		Map user = service.getUserRecordFromDbm(testDbm, username)
		assertNotNull(user)
		logger.debug(user.toString())
		
		assertEquals(username, user['username'])
		assertNotNull(user['encodedPassword'])
		assertEquals('oneGroup', user['groups'][0])
		assertNull(user['comments'])
	}
	
	@Test
	public void getUserGroupAndCommentFromDbm(){
		String username = 'userGroupAndComment'
		Map user = service.getUserRecordFromDbm(testDbm, username)
		assertNotNull(user)
		logger.debug(user.toString())
		
		assertEquals(username, user['username'])
		assertNotNull(user['encodedPassword'])
		assertEquals('oneGroup', user['groups'][0])
		assertEquals('twoGroup', user['groups'][1])
		assertEquals('thisIsComment', user['comments'])
	}

}
