package com.edgar.productManagement.controllers.errorHandlers

import com.edgar.commonlibrary.exceptions.InvalidProductDataException
import com.edgar.commonlibrary.exceptions.InvalidProductException
import com.edgar.commonlibrary.exceptions.InvalidProductListException
import com.edgar.commonlibrary.exceptions.ProductNotFoundException
import com.edgar.commonlibrary.util.ErrorMessages
import com.edgar.commonlibrary.util.ErrorResponse
import org.springframework.amqp.AmqpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Date

@RestControllerAdvice
class ProductControllerErrorHandler {

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(ex: ProductNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    ex.message ?: ErrorMessages.PRODUCT_NOT_FOUND_MESSAGE,
                    HttpStatus.NOT_FOUND.value(),
                    Date()
                )
            )
    }

    @ExceptionHandler(InvalidProductDataException::class)
    fun handleInvalidPropertyException(ex: InvalidProductDataException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    ex.message ?: ErrorMessages.GENERIC_INVALID_PRODUCT_DATA,
                    HttpStatus.NOT_FOUND.value(),
                    Date()
                )
            )
    }

    @ExceptionHandler(AmqpException::class)
    fun handleAmqpException(ex: AmqpException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .internalServerError()
            .body(
                ErrorResponse(
                     ex.message ?: ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
    }

    @ExceptionHandler(InvalidProductListException::class)
    fun handleInvalidProductListException(ex: InvalidProductListException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .internalServerError()
            .body(
                ErrorResponse(
                    ex.message ?: ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
    }

    @ExceptionHandler(InvalidProductException::class)
    fun handleInvalidProductException(ex: InvalidProductException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .internalServerError()
            .body(
                ErrorResponse(
                    ex.message ?: ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .internalServerError()
            .body(
                ErrorResponse(
                    ex.message ?: ErrorMessages.GENERIC_ERROR_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
    }
}