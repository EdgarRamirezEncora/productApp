package com.edgar.productManagement.services.impl

import com.edgar.commonlibrary.deserializers.ErrorResponseDeserializer
import com.edgar.commonlibrary.deserializers.ProductDtoDeserializer
import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.util.ConfigVariables
import com.edgar.productManagement.services.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import com.edgar.commonlibrary.util.ErrorMessages
import com.edgar.commonlibrary.util.ErrorResponse
import com.github.michaelbull.result.*
import jakarta.validation.Valid
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.http.HttpStatus
import java.util.Date

@Service
class ProductServiceImpl(
    private val rabbitTemplate: RabbitTemplate,
    private val objectMapper: ObjectMapper,
    private val productDtoDeserializer: ProductDtoDeserializer,
    private val errorResponseDeserializer: ErrorResponseDeserializer
) : ProductService {
    override fun getAllProducts() : Result<List<ProductDto>, ErrorResponse> {
        try {
            val request = mapOf("action" to "getAllProducts")

            val response  = rabbitTemplate.convertSendAndReceive(
                    ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_EXCHANGE_NAME,
                    ConfigVariables.RABBITMQ_GET_PRODUCT_LIST_ROUTING_KEY,
                    Message(objectMapper.writeValueAsString(request).encodeToByteArray())
            )

            val result = when(response) {
                is Ok<*> -> response as Ok<List<ProductDto>>
                is Err<*> -> response as Err<ErrorResponse>
                else -> Err(
                    ErrorResponse(
                        ErrorMessages.GENERIC_ERROR_MESSAGE,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Date()
                    )
                )
            }

           return when(result) {
               is Ok<List<ProductDto>> -> Ok(productDtoDeserializer.deserializerProductListDto(result.value))
               is Err<ErrorResponse> -> Err(errorResponseDeserializer.deserializeErrorResponse(result.error))
           }
        } catch (ex: Exception) {
            val errorMessage = when(ex) {
                is AmqpException -> ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE
                is IllegalArgumentException -> ErrorMessages.INVALID_PRODUCT_LIST_MESSAGE
                else -> ErrorMessages.GENERIC_ERROR_MESSAGE
            }

            return Err(
                ErrorResponse(
                    errorMessage,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
        }
    }

    override fun getProductById(id : Long) : Result<ProductDto, ErrorResponse> {
        try {
            val request = mapOf("action" to "getProduct", "productId" to id)

            val response = rabbitTemplate.convertSendAndReceive(
                ConfigVariables.RABBITMQ_GET_PRODUCT_EXCHANGE_NAME,
                ConfigVariables.RABBITMQ_GET_PRODUCT_ROUTING_KEY,
                Message(objectMapper.writeValueAsString(request).encodeToByteArray())
            )

            val result = when(response) {
                is Ok<*> -> response as Ok<ProductDto>
                is Err<*> -> response as Err<ErrorResponse>
                else -> Err(
                    ErrorResponse(
                        ErrorMessages.GENERIC_ERROR_MESSAGE,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Date()
                    )
                )
            }

            return when(result) {
                is Ok<ProductDto> -> Ok(productDtoDeserializer.deserializeProductDto(result.value))
                is Err<ErrorResponse> -> Err(errorResponseDeserializer.deserializeErrorResponse(result.error))
            }
        } catch (ex: Exception) {
            val errorMessage = when(ex) {
                is AmqpException -> ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE
                is IllegalArgumentException -> ErrorMessages.INVALID_PRODUCT_LIST_MESSAGE
                else -> ErrorMessages.GENERIC_ERROR_MESSAGE
            }

            return Err(
                ErrorResponse(
                    errorMessage,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
        }
    }

    override fun addProduct(@Valid addProductDto: AddProductDto): Result<Map<String, String>, ErrorResponse> {
        try {
            val request = mapOf("action" to "addProduct", "productData" to addProductDto)

            val message = Message(objectMapper.writeValueAsString(request).encodeToByteArray())

            val response = rabbitTemplate.convertSendAndReceive(
                ConfigVariables.RABBITMQ_ADD_PRODUCT_EXCHANGE_NAME,
                ConfigVariables.RABBITMQ_ADD_PRODUCT_ROUTING_KEY,
                message
            )

            val result = when(response) {
                is Ok<*> -> response as Ok<String>
                is Err<*> -> response as Err<ErrorResponse>
                else -> Err(
                    ErrorResponse(
                        ErrorMessages.GENERIC_ERROR_MESSAGE,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Date()
                    )
                )
            }

            return when(result) {
                is Ok<String> -> Ok(mapOf("message" to result.value))
                is Err<ErrorResponse> -> Err(errorResponseDeserializer.deserializeErrorResponse(result.error))
            }
        } catch (ex: Exception) {
            val errorMessage = when(ex) {
                is AmqpException -> ErrorMessages.GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE
                is IllegalArgumentException -> ErrorMessages.INVALID_PRODUCT_LIST_MESSAGE
                else -> ErrorMessages.GENERIC_ERROR_MESSAGE
            }

            return Err(
                ErrorResponse(
                    errorMessage,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Date()
                )
            )
        }
    }
}