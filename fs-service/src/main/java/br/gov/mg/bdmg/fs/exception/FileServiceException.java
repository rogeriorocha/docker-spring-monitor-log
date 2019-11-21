package br.gov.mg.bdmg.fs.exception;

public class FileServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileServiceException() {
		super();
	}

	public FileServiceException(String message) {
		super(message);
	}

	public FileServiceException(Throwable cause) {
		super(cause);
	}

	public FileServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}