package com.techelevator.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.ArtPiece;
import com.techelevator.model.Fee;
import com.techelevator.model.Transaction;
import com.techelevator.model.User;

@Component
public class JdbcTransactionsDAO implements TransactionsDao{

	
	
	private JdbcTemplate jdbcTemplate; 
	public JdbcTransactionsDAO (JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
		
	}

	@Override
	public int createTransaction(Transaction transaction) {
		
		sellArt(transaction.getArtID());
		
		LocalDate dateSold = LocalDate.now();
		
		
		String sql = "INSERT INTO transactions (order_id, customer_id, art_id, date_of_sale, fee, commission, total_price) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING order_id";
		
		return jdbcTemplate.queryForObject(sql, int.class, transaction.getCustomerId(), transaction.getArtID(), dateSold, transaction.getFee(), transaction.getCommission(), transaction.getTotalPrice());
		
	}


	@Override
	public void setFees(Fee newFees) {
		String sql = "UPDATE fees SET fee = ?, commission = ? WHERE fee_id = 1";
		jdbcTemplate.update(sql, newFees.getFee(), newFees.getCommission());
	}


	@Override
	public Fee getFees() {
		String sql = "SELECT fee_id, fee, commission FROM fees";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
		row.next();
		
		Fee fee = new Fee();
		fee.setFeeID(row.getInt("fee_id"));
		fee.setFee(row.getDouble("fee"));
		fee.setCommission(row.getDouble("commission"));
		
		return fee;
	}
	
	@Override
	public List<Transaction> getAllTransactions(){
		
		//
		
		String sql = "SELECT order_id, customer_id, price, transactions.art_id, date_of_sale, fee, commission, total_price, users.username, artist.artist_name, img_file_name, title FROM artist \n" + 
				"JOIN art_pieces ON art_pieces.artist_id = artist.artist_id\n" + 
				"JOIN dealer ON art_pieces.dealer_id = dealer.dealer_id\n" + 
				"JOIN users ON users.user_id = dealer.user_id\n" + 
				"JOIN transactions ON transactions.art_id = art_pieces.art_id" + 
				"";
		
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		while(row.next()) {
			
			transactions.add(mapRowToTransaction(row));
		
		}
		
		return transactions;
	}
	
	@Override
	public List<Transaction> getTransactions30Days(){
		
		String sql = "SELECT order_id, customer_id, price, transactions.art_id, date_of_sale, fee, commission, total_price, users.username, artist.artist_name, img_file_name, title FROM artist "
				+ "JOIN art_pieces ON art_pieces.artist_id = artist.artist_id "
				+ "JOIN dealer ON art_pieces.dealer_id = dealer.dealer_id "
				+ "JOIN users ON users.user_id = dealer.user_id "
				+ "JOIN transactions ON transactions.art_id = art_pieces.art_id "
				+ "WHERE (date_of_sale >= (current_date - interval '30 days'))";
		
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		while(row.next()) {
			
			transactions.add(mapRowToTransaction(row));
		
		}
		
		return transactions;
	}
	
	@Override
	public List<Transaction> getTransactions7Days(){
		
		String sql = "SELECT order_id, customer_id, price, transactions.art_id, date_of_sale, fee, commission, total_price, users.username, artist.artist_name, img_file_name, title FROM artist "
				+ "JOIN art_pieces ON art_pieces.artist_id = artist.artist_id "
				+ "JOIN dealer ON art_pieces.dealer_id = dealer.dealer_id "
				+ "JOIN users ON users.user_id = dealer.user_id "
				+ "JOIN transactions ON transactions.art_id = art_pieces.art_id "
				+ "WHERE (date_of_sale >= (current_date - interval '7 days'))";
		
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		while(row.next()) {
			
			transactions.add(mapRowToTransaction(row));
		
		}
		return transactions;
	}
	
	@Override
	public String getEmailByUsername(String name) {
		String sql = "SELECT email FROM users WHERE username = ?";
		return jdbcTemplate.queryForObject(sql, String.class, name);
	}

	
	private void sellArt(int artID) {
		
		String sql = "UPDATE art_pieces SET is_sold = true, is_available = false WHERE art_id = ?";
		
		jdbcTemplate.update(sql, artID);	
		
	}
	
	private Transaction mapRowToTransaction(SqlRowSet row) {
		
		Transaction transaction = new Transaction();
		
		transaction.setTitle(row.getString("title"));
		transaction.setImgFileName(row.getString("img_file_name"));
		transaction.setOrderId(row.getInt("order_id"));
		transaction.setCustomerId(row.getInt("customer_id"));
		transaction.setPrice(row.getDouble("price"));
		transaction.setArtID(row.getInt("art_id"));
		transaction.setDateSold(row.getDate("date_of_sale").toLocalDate());
		transaction.setFee(row.getDouble("fee"));
		transaction.setCommission(row.getDouble("commission"));
		transaction.setTotalPrice(row.getDouble("total_price"));
		transaction.setDealer(row.getString("username"));
		transaction.setArtist(row.getString("artist_name"));
		
		return transaction;

	}
	
	
	
}













