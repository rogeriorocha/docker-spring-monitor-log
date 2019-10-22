package br.gov.mg.bdmg.fsservice.exception;


public class StorageFileNotFoundException extends StorageException {



	private static final long serialVersionUID = 1559322307461685220L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}