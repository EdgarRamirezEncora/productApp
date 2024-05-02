package com.edgar.productManagement.controllers

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.productManagement.services.impl.ProductServiceImpl
import com.edgar.commonlibrary.dtos.ProductDto
import com.edgar.commonlibrary.util.ErrorMessages
import com.edgar.commonlibrary.util.ErrorResponse
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
class ProductController(private val productServiceImpl: ProductServiceImpl) {

    @GetMapping
    fun helloWorld() : ResponseEntity<String> {
        return ResponseEntity("Hello, World from Management", HttpStatus.OK)
    }

    @GetMapping("/products")
    fun getAllProducts() : ResponseEntity<Any> {
        return when(val response = productServiceImpl.getAllProducts()) {
            is Ok<List<ProductDto>> -> ResponseEntity.ok(response.value)
            is Err<ErrorResponse> -> ResponseEntity.status(response.error.statusCode).body(response.error)
        }
    }

    @GetMapping("/products/{id}")
    fun getProductById(@PathVariable("id") id : Long) : ResponseEntity<Any> {
        return when(val response = productServiceImpl.getProductById(id)) {
            is Ok -> ResponseEntity.ok(response.value)
            is Err<ErrorResponse> -> ResponseEntity.status(response.error.statusCode).body(response.error)
        }
    }

    @PostMapping("/products")
    fun createProduct(
        @Valid
        @RequestBody
        addProductDto: AddProductDto
    ) : ResponseEntity<Any> {
        return when(val response = productServiceImpl.addProduct(addProductDto)) {
            is Ok -> ResponseEntity.ok(response.value)
            is Err<ErrorResponse> -> ResponseEntity.status(response.error.statusCode).body(response.error)
        }
    }
}