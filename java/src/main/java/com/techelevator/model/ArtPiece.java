package com.techelevator.model;

import java.time.LocalDate;

public class ArtPiece {
	
	private int artID;
	private int artistId;
	private String artist;
	private int dealerId;
	private String dealer;
	private int sellerId;
	private String seller;
	private String title;
	private LocalDate dateCreated;
	private double price;
	private boolean isSold;
	private String imgFileName;
	private double feeOverride;
	private double commissionOverride;
	private boolean hasOverride;
	private boolean isSeller;
	private boolean isAvailable;
	private String tag;
	private int tagID;
	private String type;
	private int typeID;


	
	public int getTagID() {
		return tagID;
	}
	public void setTagID(int tag_id) {
		this.tagID = tag_id;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int type_id) {
		this.typeID = type_id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public int getArtID() {
		return artID;
	}
	public void setArtID(int artID) {
		this.artID = artID;
	}
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getDealerId() {
		return dealerId;
	}
	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isSold() {
		return isSold;
	}
	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}
	public String getImgFileName() {
		return imgFileName;
	}
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}
	public double getFeeOverride() {
		return feeOverride;
	}
	public void setFeeOverride(double feeOverride) {
		this.feeOverride = feeOverride;
	}
	public double getCommissionOverride() {
		return commissionOverride;
	}
	public void setCommissionOverride(double commissionOverride) {
		this.commissionOverride = commissionOverride;
	}
	public boolean isHasOverride() {
		return hasOverride;
	}
	public void setHasOverride(boolean hasOverride) {
		this.hasOverride = hasOverride;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public boolean isSeller() {
		return isSeller;
	}
	public void setSeller(boolean isSeller) {
		this.isSeller = isSeller;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtPiece other = (ArtPiece) obj;
		if (artID != other.artID)
			return false;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (artistId != other.artistId)
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (dealer == null) {
			if (other.dealer != null)
				return false;
		} else if (!dealer.equals(other.dealer))
			return false;
		if (dealerId != other.dealerId)
			return false;
		if (imgFileName == null) {
			if (other.imgFileName != null)
				return false;
		} else if (!imgFileName.equals(other.imgFileName))
			return false;
		if (isSold != other.isSold)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
