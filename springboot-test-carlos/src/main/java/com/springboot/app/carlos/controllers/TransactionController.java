package com.springboot.app.carlos.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.carlos.models.bean.TransactionModel;
import com.springboot.app.carlos.models.entity.TransactionEntity;
import com.springboot.app.carlos.service.ITransactionService;

/**
 * @author CarlosS.
 * 
 */
@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	@Autowired
	@Qualifier("transactionServiceImpl")
	private ITransactionService transactionService;
	
	//Metodo Post para crear transaccion
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionModel create(@Valid @RequestBody TransactionModel transaction) {
		return transactionService.create(transaction);
	}
	
	//Metodo Get para crear transaccion
	@GetMapping("/search/{accountIban}/{ordFilter}")
	public List<TransactionEntity> details(@PathVariable String accountIban, @PathVariable boolean ordFilter) throws Exception{
		
		return transactionService.read(accountIban, ordFilter);
	}
	
	//Metodo Get para mostrar el status
	@GetMapping("/status/{reference}/{channel}")
	public TransactionModel showStatus(@PathVariable String reference, @PathVariable String channel) throws Exception{
		
		return transactionService.getStatus(reference, channel);
//			return transactionService.getStatus(reference, channel).stream().map(transaction ->{
//				return transaction;
//			}).collect(Collectors.toList());
	}

	
}
