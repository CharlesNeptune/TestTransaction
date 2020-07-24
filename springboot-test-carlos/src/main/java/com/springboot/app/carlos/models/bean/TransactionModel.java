/**
 * 
 */
package com.springboot.app.carlos.models.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author CarlosS.
 * 
 */
public class TransactionModel implements Serializable {

	private static final long serialVersionUID = 25306925944287048L;
	
	private String reference;
	
	@NotNull
	private String accountIban;
	
	private Date date;
	
	@NotNull
	private Double amount;
	
	private Double fee;
	private String description;

	private Channel channel;
	
	private Status status;
	
	public enum Channel 
	{ 
		CLIENT, ATM, INTERNAL; 
	} 
	
	public enum Status 
	{ 
		PENDING, SETTLED, FUTURE, INVALID; 
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

	public void setDate(Date temp) {
		this.date = temp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Transaction [reference=" + reference + ", accountIban=" + accountIban + ", date=" + date + ", amount="
				+ amount + ", fee=" + fee + ", description=" + description + ", channel=" + channel + ", status="
				+ status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountIban == null) ? 0 : accountIban.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionModel other = (TransactionModel) obj;
		if (accountIban == null) {
			if (other.accountIban != null)
				return false;
		} else if (!accountIban.equals(other.accountIban))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (channel != other.channel)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fee == null) {
			if (other.fee != null)
				return false;
		} else if (!fee.equals(other.fee))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
