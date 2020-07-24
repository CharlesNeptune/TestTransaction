package com.springboot.app.carlos.models.bean;

/**
 * @author CarlosS.
 * 
 */
public class TransactionNotFounException extends IllegalArgumentException {

	private static final long serialVersionUID = -5253146488883318730L;

	public TransactionNotFounException() {
		super();
	}

	public TransactionNotFounException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TransactionNotFounException(String arg0) {
		super(arg0);
	}

	public TransactionNotFounException(Throwable arg0) {
		super(arg0);
	}

}
