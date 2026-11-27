package pe.com.condominio.controlingresos.global.exception;

import pe.com.condominio.controlingresos.global.http.HttpStatus.ErrorCode;

public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ErrorCode errorCode;
	public BusinessException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
