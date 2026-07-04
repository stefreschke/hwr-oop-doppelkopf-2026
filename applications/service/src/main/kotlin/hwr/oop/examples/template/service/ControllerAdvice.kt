package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.core.CoreException
import hwr.oop.examples.doppelkopf_2026.core.InvalidNumberOfPlayersException
import hwr.oop.examples.template.service.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
	
	@ExceptionHandler(CoreException::class)
	fun handleCoreException(e: CoreException): ResponseEntity<ErrorResponse> {
		val details = unpack(e)
		val errorResponse = ErrorResponse(
			/*status =*/ details.statusCodeInternal,
			/*error =*/ details.error,
			/*message =*/ e.message ?: details.error,
		)
		return ResponseEntity.status(details.httpStatusCode).body(errorResponse)
	}
	
	private fun unpack(coreException: CoreException): CoreExceptionHttpDetails {
		return when (coreException) {
			is InvalidNumberOfPlayersException -> CoreExceptionHttpDetails(
				statusCodeInternal = 400001,
				httpStatusCode = HttpStatus.BAD_REQUEST,
				error = "Invalid number of players provied",
			)
		}
	}
	
	/**
	 * `IllegalArgumentException` (resulting from any missed `require() { "msg" }` assertions),
	 * are probably resulting from invalid API request parameters/bodies.
	 * Thus, 400 to communicate that it is "the client's fault".
	 * If they realize that it is not their fault, they will tell us.
	 */
	@ExceptionHandler(IllegalArgumentException::class)
	fun handleIllegalArgumentExceptions(iae: Exception): ResponseEntity<ErrorResponse> {
		val status = HttpStatus.BAD_REQUEST
		val errorResponse = ErrorResponse(
			/*status =*/ 400000,
			/*error =*/ status.reasonPhrase,
			/*message =*/ iae.message ?: "An unexpected error occurred",
		)
		return ResponseEntity.status(status).body(errorResponse)
	}
	
	/**
	 * `IllegalStateException` (resulting from any missed `check() { "msg" }` assertions),
	 * are probably unexpected edge cases in domain lib and require attention from developers.
	 * Thus, 500 to communicate that it is "our fault".
	 */
	@ExceptionHandler(IllegalStateException::class)
	fun handleIllegalStateExceptions(ise: Exception): ResponseEntity<ErrorResponse> {
		val statusCode = HttpStatus.INTERNAL_SERVER_ERROR
		val errorResponse = ErrorResponse(
			/*status =*/ 500001,
			/*error =*/ "Reached invalid state",
			/*message =*/ ise.message ?: "An unexpected error occurred",
		)
		return ResponseEntity.status(statusCode).body(errorResponse)
	}
	
	/**
	 * Any unhandled exception here will be caught by this exception handler.
	 * We do not know anything about the exception (otherwise it would have gotten its own handler here).
	 * Thus, it is "our fault" (500) and we need to take a look as soon as possible.
	 */
	@ExceptionHandler(Exception::class)
	fun handleGenericException(e: Exception): ResponseEntity<ErrorResponse> {
		val statusCode = HttpStatus.INTERNAL_SERVER_ERROR
		val errorResponse = ErrorResponse(
			/*status =*/ 500000,
			/*error =*/ "An unexpected error occurred",
			/*message =*/ e.message ?: "An unexpected error occurred",
		)
		return ResponseEntity.status(statusCode).body(errorResponse)
	}
	
}

