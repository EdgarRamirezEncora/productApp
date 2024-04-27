package com.edgar.productstorage.services

import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.util.ErrorResponse
import com.github.michaelbull.result.Result
import org.springframework.amqp.core.Message


interface ProductService {
    /**
     * Listen to the GetProductList Message queue and when a message is received,
     * it verifies the action. If the action is getProductList, it returns the products
     * in the database.
     */
    fun getAllProducts(message: Message): Result<List<ProductDto>?, ErrorResponse>

    /**
     * Listen to the AddProduct Message queue and when a message is received,
     * it verifies the action. If the action is addProduct, it addProduct the received Product data.
     */
    fun addProduct(message: Message): Result<String, ErrorResponse>

    /**
     * Listen to the GetProduct Message queue and when a message is received,
     * it verifies the action. If the action is getProduct, it returns the products
     * in the database.
     */
    fun getProduct(message: Message): Result<ProductDto?, ErrorResponse>

    /**
     *  Handle the messages that could not be processed from the AddProduct Message queue
     */
    fun handleAddProductFailureMessage(message: Message)
}