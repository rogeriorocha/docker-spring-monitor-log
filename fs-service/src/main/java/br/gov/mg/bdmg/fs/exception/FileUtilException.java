package br.gov.mg.bdmg.fs.exception;

public class FileUtilException extends FileServiceException {

	private static final long serialVersionUID = 1L;

	public FileUtilException() {
	}

	public FileUtilException(String message) {
		super(message);
	}

	public FileUtilException(Throwable cause) {
		super(cause);
	}

	public FileUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
