package com.springboot.app.carlos.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.carlos.converter.TransactionConverter;
import com.springboot.app.carlos.models.bean.TransactionModel;
import com.springboot.app.carlos.models.bean.TransactionModel.Status;
import com.springboot.app.carlos.models.bean.TransactionNotFounException;
import com.springboot.app.carlos.models.entity.TransactionEntity;
import com.springboot.app.carlos.repository.TransactionRepository;

/**
 * @author CarlosS.
 * 
 */

@Service
public class TransactionServiceImpl implements ITransactionService{

	//public Map<Integer, Transaction> transactions = new HashMap<Integer, Transaction>();
	
    @Autowired
    TransactionRepository transacRepo;
    
    @Autowired
    TransactionConverter converter;
    
	/**
	 * Crea una transaccion
	 * @param transaction
	 * @return transaccion creada
	 */
	
	@Override
	@Transactional
	public TransactionModel create(TransactionModel transaction) {
		
		TransactionEntity transNewEnt = new TransactionEntity();
		
		if (validateBalance(transaction.getAmount(), transaction.getFee()) ) {
			//System.out.println("CREATE:1-SIZE:  "+transactionsList.size());
			
			//transactions.put(cont, transaction);
			transNewEnt =converter.model2entity(transaction);
			
			transacRepo.save(transNewEnt);
			return transaction;
		}
		return null;
	}

	/**
	 * Recupera una transaccion por accountIban ordenada por ordFilter ASC or DESC
	 * @param accountIban
	 * @param ordFilter
	 * @return lista de transacciones correspondientes
	 */
	@Override
	//@Transactional(readOnly = true)
	@Transactional
	public List<TransactionEntity> read(String accountIban, boolean ordFilter) {
		
		List<TransactionEntity> unsortedList = new ArrayList<>();
		
		//System.out.println("READ:1-0  "+unsortedList.get(0));
		if(accountIban != null) {
			 unsortedList = filterByAccount(accountIban);
		}		
		return orderByFilter(unsortedList, ordFilter);
	
	}
	
	/**
	 * Recupera el estado de una transaccion por ref y channel(opcional) 
	 * @param ref
	 * @param channel
	 * @return transacciones por ref y channel
	 * @throws ParseException 
	 * @throws TransactionNotFounException si la ref es null
	 */
	@Override
	@Transactional
	public TransactionModel getStatus(String ref, String channel) throws ParseException{
		validateRef(ref);
		
		List<TransactionEntity> allList = transacRepo.findAll();
		List<TransactionModel> transNewEnt = allList.stream().map(transactionEntity -> converter.entity2model(transactionEntity))
				.collect(Collectors.toList());
		
		TransactionModel transacSel = new TransactionModel();

		if(ref != null) {
			transNewEnt = transNewEnt.stream().filter(TransactionModel -> TransactionModel.getReference().contentEquals(ref))
					.collect(Collectors.toList());
			
			transNewEnt.forEach(System.out::println);   
			if(!transNewEnt.isEmpty()) {
				transacSel = logicStatus(transNewEnt.get(0), channel);
			}else{
				transacSel.setReference(ref);
				transacSel.setStatus(Status.INVALID);
			}
			
		}		
			
		return transacSel;
	
	}
	
	/**
	 * Ordena el resultado de las listas ASC or DESC
	 * @param unsortedList
	 * @param ordFilter
	 * @return sortedList ordendor según se indique
	 */
	@Transactional
	private List<TransactionEntity> orderByFilter(List<TransactionEntity> unsortedList, boolean ordFilter) {
		
		List<TransactionEntity> sortedList = transacRepo.findAll();
		//ASC
		if(ordFilter) {
			sortedList = unsortedList.stream().sorted(Comparator.comparingDouble(TransactionEntity::getAmount))
					.collect(Collectors.toList());
		//DESC
		}else{
			sortedList = unsortedList.stream().sorted(Comparator.comparingDouble(TransactionEntity::getAmount).reversed())
					.collect(Collectors.toList());
		}
		return sortedList;
	}
	
	/**
	 * Valida existencia de una transaccion
	 * @param accountIban
	 * @throws TransactionNotFounException si el iban no se encuentra
	 */
	@Transactional(readOnly = true)
	private List<TransactionEntity> filterByAccount(String accountIban) {

		List<TransactionEntity> commonList = transacRepo.findAll();
		
		commonList = commonList.stream().filter(Transaction -> Transaction.getAccountIban().contentEquals(accountIban))
				.collect(Collectors.toList());
		
		if (commonList.isEmpty()) {
			throw new TransactionNotFounException("No se ha encontrado la transacción por iban");
		}
		
		return commonList;	
	}
	
	/**
	 * Valida existencia de una referencia
	 * @param ref
	 * @throws TransactionNotFounException si la ref es null
	 */
	@Transactional(readOnly = true)
	private void validateRef(String ref) {
		List<TransactionEntity> unsortedList =transacRepo.findAll();
		
		Stream<TransactionEntity> tra = unsortedList.stream().filter(transaction -> ref.isEmpty());
		//tra.forEach(System.out::println);  
		if (tra == null  ) {
			throw new TransactionNotFounException("No se ha encontrado la transacción por ref");
		}
//		List<String> lines = Arrays.asList("spring", "node", "mkyong");
//
//        List<String> result = lines.stream()                // convert list to stream
//                .filter(line -> !"mkyong".equals(line))     // we dont like mkyong
//                .collect(Collectors.toList());              // collect the output and convert streams to a List
//
//        result.forEach(System.out::println);    
//		
	}
	
	/**
	 * Valida el valor mayor que 0 del saldo en cuenta
	 * @param amount
	 * @return boolean true si es mayor 0 o false si no
	 */
	//TODO
	@Transactional(readOnly = true)
	private boolean validateBalance(Double amount, Double fee) {
		if(amount < 0  && (amount-fee)< 0){
			System.out.println("ERROR: Account balance below 0 NOT ALLOWED");
			return false;
		}
		return true;
	}
	
	/**
	 * Tiene la logica de negocio de todo el Status
	 * @param transaction
	 * @param channel
	 * @return Transaction
	 * @throws ParseException 
	 */
	@Transactional
	private TransactionModel logicStatus(TransactionModel transaction, String channel ) throws ParseException {
		TransactionModel transacSel = new TransactionModel();
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		Date endDate = fmt.parse(fmt.format(today).toString());
		Date starDate = null;
		
		transacSel.setReference(transaction.getReference());
		transacSel.setAmount(transaction.getAmount());
		transacSel.setFee(transaction.getFee());
		transacSel.setDate(transaction.getDate());
		
		starDate=fmt.parse(fmt.format(transacSel.getDate()).toString());
		
		if (channel.equals("CLIENT") && starDate.after(endDate)) {
				transacSel.setStatus(Status.FUTURE);
				transacSel.setAmount(transacSel.getAmount()-transacSel.getFee());
			}else if(channel.equals("CLIENT") || channel.equals("ATM") ){
			if(starDate.before(endDate)) {
				transacSel.setStatus(Status.SETTLED);
				transacSel.setAmount(transacSel.getAmount()-transacSel.getFee()) ;
			}else if (channel.equals("ATM") && starDate.equals(endDate)
											|| starDate.after(endDate)) {
				transacSel.setStatus(Status.PENDING);
				transacSel.setAmount(transacSel.getAmount()-transacSel.getFee());
			}
			else if(channel.equals("CLIENT")) {
					transacSel.setStatus(Status.PENDING);
			}
		}else if(channel.equals("INTERNAL")){
			 if (fmt.format(transacSel.getDate()).equals(fmt.format(today))) {
				 transacSel.setStatus(Status.PENDING);
			 }else if (starDate.after(endDate)) {
				 transacSel.setStatus(Status.FUTURE);
			 }else {
				 transacSel.setStatus(Status.SETTLED);
			 }
		}
		
		return transacSel;
	}
	
	
}
