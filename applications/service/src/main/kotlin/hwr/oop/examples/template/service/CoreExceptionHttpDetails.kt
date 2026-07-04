package hwr.oop.examples.template.service

import org.springframework.http.HttpStatus

internal data class CoreExceptionHttpDetails(
	val httpStatusCode: HttpStatus,
	val statusCodeInternal: Int,
	val error: String,
)