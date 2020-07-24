package com.springboot.app.carlos.converter;

import org.springframework.stereotype.Component;

import com.springboot.app.carlos.models.bean.TransactionModel;
import com.springboot.app.carlos.models.entity.TransactionEntity;

@Component("transactionConverter")
public class TransactionConverter {
	

		public TransactionModel entity2model(TransactionEntity transac) {
			TransactionModel traModel = new TransactionModel();
			traModel.setReference(transac.getReference());
			traModel.setAccountIban(transac.getAccountIban());
			traModel.setDate(transac.getDate());
			traModel.setAmount(transac.getAmount());
			traModel.setFee(transac.getFee());
			traModel.setDescription(transac.getDescription());
			return traModel;
		}
		
		public TransactionEntity model2entity(TransactionModel traModel) {
			TransactionEntity transac = new TransactionEntity();
			transac.setReference(traModel.getReference());
			transac.setAccountIban(traModel.getAccountIban());
			transac.setDate(traModel.getDate());
			transac.setAmount(traModel.getAmount());
			transac.setFee(traModel.getFee());
			transac.setDescription(traModel.getDescription());
			return transac;
		}

		
}
