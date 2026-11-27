package pe.com.condominio.controlingresos.global.exception;


import java.nio.file.AccessDeniedException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import pe.com.condominio.controlingresos.global.http.HttpStatus.ErrorCode;
import pe.com.condominio.controlingresos.global.response.ApiErrorResponse;
import pe.com.condominio.controlingresos.global.response.ApiFieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest req){
		ErrorCode ec = ex.getErrorCode();
		return build(ec,ex.getMessage(),req,null);	
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest req){
		List<ApiFieldError> details = ex.getBindingResult().getFieldErrors().stream()
				.map(f -> new ApiFieldError(f.getField(),f.getDefaultMessage()))
				.toList();
		return build(ErrorCode.VALIDATION_ERROR,"Hay errores de validación",req,details);
	}
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req){
    	return build(ErrorCode.VALIDATION_ERROR,"El cuerpo de la peticion es inválido",req,null);
    }
    
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex,HttpServletRequest req){
		return build(ErrorCode.INVALID_CREDENTIALS,"Credenciales incorrectas.",req,null);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req){
		return build(ErrorCode.ACCESS_DENIED,"No tienes permisos para este recurso",req,null);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex,HttpServletRequest req){
		log.error("Error no controlado en {}",req.getRequestURI(),ex);
		return build(ErrorCode.INTERNAL_ERROR,"Ocurrio un error interno",req,null);
	}
	
	private ResponseEntity<ApiErrorResponse> build(ErrorCode ec,String message,
			HttpServletRequest req, List<ApiFieldError> details ){
		ApiErrorResponse body = ApiErrorResponse.of(ec.getStatus().value(), ec.name(),message,
				req.getRequestURI(), details);
		return ResponseEntity.status(ec.getStatus()).body(body);
	}

}
