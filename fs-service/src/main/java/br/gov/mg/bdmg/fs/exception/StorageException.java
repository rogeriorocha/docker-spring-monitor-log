package br.gov.mg.bdmg.fs.exception;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 1211231L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}