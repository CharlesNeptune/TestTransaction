package com.springboot.app.carlos.service;

import java.text.ParseException;
import java.util.List;

import com.springboot.app.carlos.models.bean.TransactionModel;
import com.springboot.app.carlos.models.entity.TransactionEntity;

public interface ITransactionService {

	public abstract TransactionModel create(TransactionModel transaction);

	public abstract List<TransactionEntity> read(String accountIban,  boolean amount);
	
	public abstract TransactionModel getStatus(String reference, String channel) throws ParseException;
}
