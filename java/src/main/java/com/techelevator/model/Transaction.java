package com.techelevator.model;

import java.time.LocalDate;

public class Transaction{

		private int orderId;
		private int customerId;
		private LocalDate dateSold;
		private int artId;
		private int artistId;
		private String artist;
		private int dealerId;
		private String dealer;
		private String title;
		private LocalDate dateCreated;
		private double price;
		private String imgFileName;
		private double fee;
		private double commission;
		private double totalPrice;
		
		public int getArtID() {
			return artId;
		}
		public void setArtID(int artID) {
			this.artId = artID;
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
		public String getImgFileName() {
			return imgFileName;
		}
		public void setImgFileName(String imgFileName) {
			this.imgFileName = imgFileName;
		}
		public int getOrderId() {
			return orderId;
		}
		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}
		public int getCustomerId() {
			return customerId;
		}
		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}
		public LocalDate getDateSold() {
			return dateSold;
		}
		public void setDateSold(LocalDate dateSold) {
			this.dateSold = dateSold;
		}
		public double getFee() {
			return fee;
		}
		public void setFee(double fee) {
			this.fee = fee;
		}
		public double getCommission() {
			return commission;
		}
		public void setCommission(double comission) {
			this.commission = comission;
		}
		public double getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}
}
