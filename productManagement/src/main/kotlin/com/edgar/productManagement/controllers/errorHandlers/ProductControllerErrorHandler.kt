package com.edgar.productManagement.controllers.errorHandlers

import com.edgar.commonlibrary.util.ErrorMessages
import com.edgar.commonlibrary.util.ErrorResponse
import jakarta.validation.ValidationException
import org.springframework.amqp.AmqpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Date

@RestControllerAdvice
class ProductControllerErrorHandler {

    @ExceptionHandler(
        ValidationException::class,
        AmqpException::class,
        MethodArgumentNotValidException::class,
        Exception::class
    )
    fun errorHandler(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorMessage = when(ex) {
            is AmqpException -> ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE
            is MethodArgumentNotValidException -> ErrorMessages.GENERIC_INVALID_PRODUCT_DATA
            else -> ErrorMessages.GENERIC_ERROR_MESSAGE
        }

        val status = when (ex) {
            is MethodArgumentNotValidException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity
            .status(status)
            .body(
                ErrorResponse(
                    errorMessage,
                    status.value(),
                    Date()
                )
            )
    }
}