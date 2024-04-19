package com.edgar.productstorage.services

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.commonlibrary.dtos.ProductDto
import com.fasterxml.jackson.core.JsonProcessingException


interface ProductService {
    /**
     * Listen to the GetProductList Message queue and when a message is received,
     * it verifies the action. If the action is getProductList, it returns the products
     * in the database.
     */
    fun getAllProducts(requestContent: String): List<ProductDto>?

    /**
     * Listen to the AddProduct Message queue and when a message is received,
     * it verifies the action. If the action is addProduct, it addProduct the received Product data.
     */
    fun addProduct(requestContent: String): Boolean

    /**
     * Listen to the GetProduct Message queue and when a message is received,
     * it verifies the action. If the action is getProduct, it returns the products
     * in the database.
     */
    fun getProduct(requestContent: String): ProductDto?
}