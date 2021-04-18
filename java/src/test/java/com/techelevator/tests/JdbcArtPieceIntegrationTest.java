package com.techelevator.tests;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.dao.ArtPieceDAO;
import com.techelevator.dao.JdbcArtPieceDao;
import com.techelevator.model.ArtPiece;

import org.junit.Assert;



public class JdbcArtPieceIntegrationTest  extends DAOIntegrationTest {

 
	private ArtPieceDAO artDao;
	private JdbcTemplate jdbcTemplate;

	private static final String USERNAME_DUMMY = "DUMMYuser";
	private static final String PASS_DUMMY = "DUMMYpass";
	private static final String ROLE_DUMMY = "ROLE_ADMIN";
	private static final String ARTIST_NAME_DUMMY = "DUMMYArtist";
	private static final String TITLE_DUMMY = "DUMMYTitle";
	private LocalDate dateCreatedDummy = LocalDate.now();
	private static final double PRICE_DUMMY = 99.99;
	private static final String IMG_FILE_DUMMY = "DUMMYimagefile";
	
	private int artistId;
	private int dealerId;


	@BeforeClass
    public static void setupData() {
    	setupDataSource();
    	
    }
    
    @AfterClass
    public static void closeData() throws SQLException {
    	closeDataSource();
    }


    
    @After
    public void roll() throws SQLException {
        rollback();
    }
	@Before
	public void setup() {
		
	    DataSource dataSource = this.getDataSource();
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		artDao = new JdbcArtPieceDao(jdbcTemplate);
		
		// create user (dealer)
		
		String sql = "INSERT INTO users (user_id, username, password_hash, role, email) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING user_id";
		
		int dealerUserID = jdbcTemplate.queryForObject(sql, int.class, USERNAME_DUMMY, PASS_DUMMY, ROLE_DUMMY, "test@email");
	
		// create dealer
		
		sql = "INSERT INTO dealer (dealer_id, user_id) VALUES (DEFAULT, ?) RETURNING dealer_id";
		this.dealerId = jdbcTemplate.queryForObject(sql, int.class, dealerUserID);
					
		// create artist id
		sql = "INSERT INTO artist (artist_id, artist_name) VALUES (DEFAULT, ?) RETURNING artist_id";
		this.artistId = jdbcTemplate.queryForObject(sql, int.class, ARTIST_NAME_DUMMY);
		
		
		}
	
	
	@Test
	public void create_listing() {
		ArtPiece testArtPiece = testArtPiece();		
		int artID = artDao.createListing(testArtPiece);

		Assert.assertTrue(artID > 0);
		
	}

	@Test
	public void get_all_listing() {

		ArtPiece testArtPiece2 = testArtPiece();
		artDao.createListing(testArtPiece2);
	
		List <ArtPiece> listings = artDao.getAllListings();
		
		
		boolean working = false;
		for (ArtPiece testArt : listings) {
			if (testArt.getArtist().equals(ARTIST_NAME_DUMMY)) {
				working = true;
			}
		}
		
		Assert.assertTrue(working);

	
	}
	
	@Test
	public void get_listing_by_art_id() {		
		ArtPiece testArtPiece = testArtPiece();
		int artID = artDao.createListing(testArtPiece);
		
		ArtPiece result = artDao.getListingByArtID(artID);
		
		Assert.assertEquals(artID, result.getArtID());
		
	}
	
	@Test
	public void update_listing() {
		ArtPiece testArtPiece = testArtPiece();		
		int artID = artDao.createListing(testArtPiece);		
		testArtPiece = artDao.getListingByArtID(artID);
		testArtPiece.setTitle("updatedTestTitle");
		
		artDao.updateArtPiece(testArtPiece);
		
		ArtPiece updatedArtPiece = artDao.getListingByArtID(artID);
		Assert.assertTrue(updatedArtPiece.getTitle().equals("updatedTestTitle"));
	}

	@Test
	public void delete_listing() {
		ArtPiece testArtPiece = testArtPiece();		
		int artID = artDao.createListing(testArtPiece);		
		int initialListSize = artDao.getAllListings().size();
		
		artDao.deleteArtPiece(artID);
		int resultListSize = artDao.getAllListings().size();
		
		Assert.assertEquals(initialListSize - 1, resultListSize);
		
	}

	
	
	

	
	private ArtPiece testArtPiece() {
		
		ArtPiece testArt = new ArtPiece();

		testArt.setArtist(ARTIST_NAME_DUMMY);
		testArt.setDealer(USERNAME_DUMMY);
		testArt.setTitle(TITLE_DUMMY);
		testArt.setDateCreated(dateCreatedDummy);
		testArt.setPrice(PRICE_DUMMY);
		testArt.setImgFileName(IMG_FILE_DUMMY);
		testArt.setType("Drawing");
		testArt.setTypeID(1);
		return testArt;
	}

	
	
}
