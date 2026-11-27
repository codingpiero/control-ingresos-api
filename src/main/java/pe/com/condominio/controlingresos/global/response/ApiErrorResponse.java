package pe.com.condominio.controlingresos.global.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(boolean success,
		int status,
		String code,
		String message,
		String path,
		Instant timestamp,
		List<ApiFieldError> details) 
{
	public static ApiErrorResponse of(int status,String code, String message,String path) {
		return new ApiErrorResponse(false,status,code,message,path,Instant.now(),null);
	}
	public static ApiErrorResponse of(int status, String code, String message, String path,List<ApiFieldError> details) {
		return new ApiErrorResponse(false, status, code, message, path, Instant.now(), details);
	}
}
