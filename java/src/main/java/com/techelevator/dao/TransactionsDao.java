package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Fee;
import com.techelevator.model.Transaction;
import com.techelevator.model.User;

public interface TransactionsDao {

	
	public int createTransaction(Transaction transaction);
	public void setFees(Fee newFees);
	public Fee getFees();
	public List<Transaction> getAllTransactions();
	List<Transaction> getTransactions30Days();
	List<Transaction> getTransactions7Days();
	public String getEmailByUsername(String user);

}
