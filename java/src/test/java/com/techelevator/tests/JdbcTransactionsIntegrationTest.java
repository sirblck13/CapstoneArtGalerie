package com.techelevator.tests;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.dao.ArtPieceDAO;
import com.techelevator.dao.JdbcTransactionsDAO;
import com.techelevator.dao.TransactionsDao;
import com.techelevator.model.ArtPiece;
import com.techelevator.model.Transaction;


public class JdbcTransactionsIntegrationTest extends DAOIntegrationTest{
	
	private TransactionsDao dao;
	private JdbcTemplate jdbcTemplate;
	private static final int ARTIST_ID_DUMMY = 88888;
	private static final int DEALER_ID_DUMMY = 77777;
	private static final int USER_ID_DUMMY = 11111;
	private static final String USERNAME_DUMMY = "DUMMYuser";
	private static final String PASS_DUMMY = "DUMMYpass";
	private static final String ROLE_DUMMY = "ROLE_ADMIN";
	private static final String ARTIST_NAME_DUMMY = "DUMMYArtist";
	private static final String TITLE_DUMMY = "DUMMYTitle";
	private LocalDate dateCreatedDummy = LocalDate.now();
	private LocalDate date7DaysAgo = LocalDate.now().minusDays(7);
	private LocalDate date30DaysAgo = LocalDate.now().minusDays(30);
	private static final double PRICE_DUMMY = 99.99;
	private static final String IMG_FILE_DUMMY = "DUMMYimagefile";
	private static final int COSTUMER_ID_DUMMY = 66666;
	private static final int USER_ID_COSTUMER_DUMMY = 55555;
	private static final String COSTUMER_USERNAME_DUMMY = "DUMMYcostumerUser";
	private static final String PASS_COSTUMER_DUMMY = "DUMMYpass";
	private static final String ROLE_COSTUMER_DUMMY = "ROLE_USER";
	private static final int ART_ID_DUMMY = 00000;
	private static final int ORDER_ID_DUMMY = 4637;
	private static final double FEE_DUMMY = 0.1;
	private static final double COMMISSION_DUMMY = 0.5;
	private static final double TOTAL_PRICE_DUMMY = 159.98;
	
	private int costumerId;
	private int artId;
	
	
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
		dao = new JdbcTransactionsDAO(jdbcTemplate);
		
		// create user (dealer)
		
		String sql = "INSERT INTO users (user_id, username, password_hash, role, email) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING user_id";
		


		int dealerUserID = jdbcTemplate.queryForObject(sql, int.class, USERNAME_DUMMY, PASS_DUMMY, ROLE_DUMMY, "test@email.com");

				
		// create user2 (customer)
		
		sql = "INSERT INTO users (user_id, username, password_hash, role, email) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING user_id";

		int costumerUserId = jdbcTemplate.queryForObject(sql, int.class, COSTUMER_USERNAME_DUMMY, PASS_COSTUMER_DUMMY, ROLE_COSTUMER_DUMMY, "test@email.com");

	
		// create dealer
		
		sql = "INSERT INTO dealer (dealer_id, user_id) VALUES (DEFAULT, ?) RETURNING dealer_id";
		int dealerId = jdbcTemplate.queryForObject(sql, int.class, dealerUserID);
		
		// create customer
		sql = "INSERT INTO customer (customer_id, user_id) VALUES (DEFAULT, ?) RETURNING customer_id";
		this.costumerId = jdbcTemplate.queryForObject(sql, int.class, costumerUserId);
			
		// create artist id
		sql = "INSERT INTO artist (artist_id, artist_name) VALUES (DEFAULT, ?) RETURNING artist_id";
		int artistId = jdbcTemplate.queryForObject(sql, int.class, ARTIST_NAME_DUMMY);
		 
		// create art piece for sale
		sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, dealer_id) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING art_id";
		this.artId = jdbcTemplate.queryForObject(sql, int.class,TITLE_DUMMY, dateCreatedDummy, PRICE_DUMMY, IMG_FILE_DUMMY, artistId, dealerId);
		 
//		sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, dealer_id) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING art_id";
//		this.artId = jdbcTemplate.queryForObject(sql, int.class,TITLE_DUMMY, date7DaysAgo, PRICE_DUMMY, IMG_FILE_DUMMY, artistId, dealerId);
//
//		sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, dealer_id) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING art_id";
//		this.artId = jdbcTemplate.queryForObject(sql, int.class,TITLE_DUMMY, dat30DaysAgo, PRICE_DUMMY, IMG_FILE_DUMMY, artistId, dealerId);

	}
	
		@Test
		public void create_transaction() {
			
			Transaction testTransaction = testTransaction();
			
			int orderID = dao.createTransaction(testTransaction);
			
			Assert.assertTrue(orderID > 0);
			
		}
	
		
		@Test
		public void get_all_transactions() {
			
			int startSize = dao.getAllTransactions().size();
			
			
			Transaction testTransaction = testTransaction();
			
			dao.createTransaction(testTransaction);
			
			int endSize = dao.getAllTransactions().size();
			
			Assert.assertEquals(startSize + 1 , endSize);
			
		}
		
		@Test
		public void get_transactions_30() {
			
			int startSize = dao.getTransactions30Days().size();
			
			Transaction testTransaction = testTransaction();
			
			int orderID = dao.createTransaction(testTransaction);
			
			String sql = "UPDATE transactions SET date_of_sale = ? WHERE order_id = ?";
			jdbcTemplate.update(sql, date30DaysAgo, orderID);
			
			int endSize = dao.getTransactions30Days().size();
			
			Assert.assertEquals(startSize + 1 , endSize);
		}
		
		@Test
		public void get_transactions_7() {
			
			int startSize = dao.getTransactions7Days().size();
			
			Transaction testTransaction = testTransaction();
			
			int orderID = dao.createTransaction(testTransaction);
			
			String sql = "UPDATE transactions SET date_of_sale = ? WHERE order_id = ?";
			jdbcTemplate.update(sql, date7DaysAgo, orderID);
			
			int endSize = dao.getTransactions7Days().size();
			
			Assert.assertEquals(startSize + 1 , endSize);
			
		}
		
		private Transaction testTransaction() {
			
			Transaction test = new Transaction();
			test.setDateSold((dateCreatedDummy));
			test.setCustomerId(this.costumerId);
			test.setTotalPrice(TOTAL_PRICE_DUMMY);
			test.setArtID(this.artId);
			test.setFee(FEE_DUMMY);
			test.setCommission(COMMISSION_DUMMY);
			return test;
		}

	
//		public Transaction receiptTest() {
//			Transaction transaction = new Transaction();
//			
//			transaction.setCustomerId(costumerUserId);
//			transaction.setArtID(row.getInt("art_id"));
//			transaction.setDateSold(row.getDate("date_of_sale").toLocalDate());
//			transaction.setFee(row.getDouble("fee"));
//			transaction.setCommission(row.getDouble("commission"));
//			transaction.setTotalPrice(row.getDouble("total_price"));
//			transaction.setDealer(row.getString("username"));
//			transaction.setArtist(row.getString("artist_name"));
//			
//			return transaction;
//		}
	
	
	
	
	
	

}
