package com.techelevator.dao;

import java.util.ArrayList;


import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.ArtPiece;


@Component
public class JdbcArtPieceDao implements ArtPieceDAO{
	
	private JdbcTemplate jdbcTemplate; 
	public JdbcArtPieceDao (JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;;
	}

	@Override
	public int createListing(ArtPiece artPiece) {
		
		int artistId = getArtistId(artPiece.getArtist());
		
		
		SqlRowSet result = null;
		if((!artPiece.getDealer().equals("")) && (artPiece.getTagID() > 0)) {
			int dealerId = getDealerId(artPiece.getDealer());
			String sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, dealer_id, tag_id, type_id) " + 
					"VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING art_id";
			result = jdbcTemplate.queryForRowSet(sql, artPiece.getTitle(), artPiece.getDateCreated(), artPiece.getPrice(), artPiece.getImgFileName(), artistId, dealerId, artPiece.getTagID(), artPiece.getTypeID());
			
		} 
		if ((artPiece.getDealer().equals("") && (artPiece.getTagID() > 0))){
			
			String sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, tag_id, type_id) "
					+ "VALUES (DEFAULT, ?, CAST ( ? AS DATE ), ?, ?, ?, ?, ?) RETURNING art_id";
			result = jdbcTemplate.queryForRowSet(sql, artPiece.getTitle(), artPiece.getDateCreated(), artPiece.getPrice(), artPiece.getImgFileName(), artistId, artPiece.getTagID(), artPiece.getTypeID());
			
		}
		
		if ((!artPiece.getDealer().equals("")) && (artPiece.getTagID() == 0) ) {
			
			int dealerId = getDealerId(artPiece.getDealer());
			String sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, dealer_id, type_id) " + 
					"VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?) RETURNING art_id";
			result = jdbcTemplate.queryForRowSet(sql, artPiece.getTitle(), artPiece.getDateCreated(), artPiece.getPrice(), artPiece.getImgFileName(), artistId, dealerId, artPiece.getTypeID());
			
		}
		
		if ((artPiece.getDealer().equals("") && (artPiece.getTagID() == 0))) {
			
			String sql = "INSERT INTO art_pieces (art_id, title, date_created, price, img_file_name, artist_id, type_id) "
					+ "VALUES (DEFAULT, ?, CAST ( ? AS DATE ), ?, ?, ?, ?, ?) RETURNING art_id";
			result = jdbcTemplate.queryForRowSet(sql, artPiece.getTitle(), artPiece.getDateCreated(), artPiece.getPrice(), artPiece.getImgFileName(), artistId, artPiece.getTypeID());
			
		}
	
		
			int artID = 0;
			if(result.next()) {
				artID = result.getInt("art_id");
			}
		return artID;
	}

	@Override
	public List<ArtPiece> getAllListings() {
		
		String sql = "SELECT art_id, title, date_created, price, img_file_name, is_sold, artist.artist_id, artist_name, dealer_id, override_fee, override_commission, has_override, is_available, tag, tags.tag_id, art_type, art_type.type_id " + 
				"FROM art_pieces " + 
				"JOIN artist ON artist.artist_id = art_pieces.artist_id " + 
				"JOIN tags ON tags.tag_id = art_pieces.tag_id " + 
				"JOIN art_type ON art_type.type_id = art_pieces.type_id";
		
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
		
		List<ArtPiece> listOfArt = new ArrayList<ArtPiece>();
		while(row.next()) {
			ArtPiece art = mapRowToArt(row);
			art = mapArtistSeller(art);
			
			if (art.getDealerId() > 0) {
				art = mapDealerToArt(art);
			}
			
			listOfArt.add(art);
		}
		return listOfArt;
	}
	
	
	
	@Override
	public ArtPiece getListingByArtID(int artID) {
	
		String sql = "SELECT art_id, title, date_created, price, img_file_name, is_sold, artist.artist_id, artist_name, dealer.dealer_id, username, override_fee, override_commission, has_override, is_available, tag, tags.tag_id, art_type, art_type.type_id " + 
				"FROM art_pieces " + 
				"JOIN artist ON artist.artist_id = art_pieces.artist_id " + 
				"JOIN dealer ON dealer.dealer_id = art_pieces.dealer_id " + 
				"JOIN users ON users.user_id = dealer.user_id " + 
				"JOIN tags ON tags.tag_id = art_pieces.tag_id " + 
				"JOIN art_type ON art_type.type_id = art_pieces.type_id " + 
				"WHERE art_id = ?";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql, artID);
		
		row.next();
			
		ArtPiece art = mapRowToArt(row);
		art = mapArtistSeller(art);
		
		if (art.getDealerId() > 0) {
			art = mapDealerToArt(art);
		}
		return art;
	}
	
	@Override
	public void updateArtPiece(ArtPiece updatedArtPiece) {
		int artistId = getArtistId(updatedArtPiece.getArtist());
		
		if (!updatedArtPiece.getDealer().equals("")) {
			
			int dealerId = getDealerId(updatedArtPiece.getDealer());
			String sql = "UPDATE art_pieces SET title = ?, date_created = ?, price = ?, img_file_name = ?, artist_id = ?, dealer_id = ? WHERE art_id = ? RETURNING art_id";
			jdbcTemplate.queryForRowSet(sql, updatedArtPiece.getTitle(), updatedArtPiece.getDateCreated(), updatedArtPiece.getPrice(), updatedArtPiece.getImgFileName(), artistId, dealerId, updatedArtPiece.getArtID());
			
		} else {
			String sql = "UPDATE art_pieces SET title = ?, date_created = ?, price = ?, img_file_name = ?, artist_id = ? WHERE art_id = ? RETURNING art_id";
			jdbcTemplate.queryForRowSet(sql, updatedArtPiece.getTitle(), updatedArtPiece.getDateCreated(), updatedArtPiece.getPrice(), updatedArtPiece.getImgFileName(), artistId, updatedArtPiece.getArtID());
		}
	}

	@Override
	public void deleteArtPiece(int artID) {
		String sql = "DELETE FROM art_pieces WHERE art_id = ?";

		
		jdbcTemplate.update(sql, artID);
	}
	
	@Override
	public void overrideFee(ArtPiece art) {
		String sql = "UPDATE art_pieces SET override_fee = ?, has_override = true WHERE art_id = ?";
		jdbcTemplate.update(sql, art.getFeeOverride(), art.getArtID());
		
	}
	
	@Override
	public void overrideCommision(ArtPiece art) {
		String sql = "UPDATE art_pieces SET override_commission = ?, has_override = true WHERE art_id = ?";
		jdbcTemplate.update(sql, art.getCommissionOverride(), art.getArtID());
		
	}
	
	@Override
	public void setFeesToDefault(int artID) {
		String sql = "UPDATE art_pieces SET override_fee = null, override_commission = null, has_override = false WHERE art_id = ?";
		jdbcTemplate.update(sql, artID);
	}
	
	@Override
	public void flipAvailable(int artID) {
		ArtPiece art = getListingByArtID(artID);
		boolean flippedStatus = !(art.isAvailable());
		String sql = "UPDATE art_pieces SET is_available = ? WHERE art_id = ?";
		jdbcTemplate.update(sql, flippedStatus, artID);
	}

	private ArtPiece mapRowToArt(SqlRowSet row) {
		
		ArtPiece art = new ArtPiece();
		
		art.setArtist(row.getString("artist_name"));
		art.setArtID(row.getInt("art_id"));
		art.setTitle(row.getString("title"));
		art.setDateCreated(row.getDate("date_created").toLocalDate());
		art.setPrice(row.getDouble("price"));
		art.setImgFileName(row.getString("img_file_name"));
		art.setSold(row.getBoolean("is_sold"));
		art.setArtistId(row.getInt("artist_id"));
		art.setHasOverride(row.getBoolean("has_override"));
		art.setAvailable(row.getBoolean("is_available"));
		art.setType(row.getString("art_type"));
		art.setTypeID(row.getInt("type_id"));
		art.setTagID(row.getInt("tag_id"));
		art.setTag(row.getString("tag"));
	
		
		if (row.getDouble("dealer_id") > 0) {
			art.setDealerId(row.getInt("dealer_id"));
		}
		
		if (row.getDouble("override_commission") >= 0 ) {
			art.setCommissionOverride(row.getDouble("override_commission"));
		}
		if (row.getDouble("override_fee") >= 0 ) {
			art.setFeeOverride(row.getDouble("override_fee"));
		}
		
		return art;
		
	}
	
	private int getArtistId(String artistName) {
		
		String sql = "SELECT artist_id FROM artist WHERE artist_name = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, artistName);
	}
	
	private int getDealerId(String dealerName) {
		
		String sql = "SELECT dealer_id FROM dealer JOIN users ON users.user_id = dealer.user_id WHERE username = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, dealerName);
	}
	
	private ArtPiece mapArtistSeller(ArtPiece art) {
		
		String sql = "SELECT artist_id, user_id, artist_name, is_seller FROM artist WHERE artist_id = ?";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql, art.getArtistId());
		row.next();
		art.setSeller(row.getBoolean("is_seller"));
		
		return art;
	}
	
	private ArtPiece mapDealerToArt(ArtPiece art) {
		
		String sql = "SELECT username FROM dealer " + 
				"JOIN users ON users.user_id = dealer.user_id WHERE dealer_id = ?";
		String dealerName = jdbcTemplate.queryForObject(sql, String.class, art.getDealerId());
		
		art.setDealer(dealerName);
		
		return art;
	}
	



	
}
