package com.springboot.app.carlos.models.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author CarlosS.
 * 
 */
@Entity
@Table(name="transactions")
public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = -5253146488883318730L;

	@Id
	@GenericGenerator(
		name = "assigned-identity",
		strategy = "com.springboot.app.carlos.repository.ReferenceGenerator"
	)
	@GeneratedValue(
		generator = "assigned-identity",
		strategy = GenerationType.IDENTITY
	)
	@Column(name = "IDENTIFIER", nullable = false, unique = true)
	private String reference;

	@Column(length = 25, name = "account_iban", nullable = false)
	private String accountIban;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(nullable = false)
	private double amount;
	
	private double fee;
	
	private String description;

	
	public TransactionEntity() {
	}
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccountIban() {
		return accountIban;
	}

	public void setAccountIban(String accountIban) {
		this.accountIban = accountIban;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionEntity(String reference, String accountIban, Date date, double amount, double fee,
			String description) {
		super();
		this.reference = reference;
		this.accountIban = accountIban;
		this.date = date;
		this.amount = amount;
		this.fee = fee;
		this.description = description;
	}
	
}
