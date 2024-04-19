package com.edgar.productManagement.controllers

import com.edgar.commonlibrary.dtos.AddProductDto
import com.edgar.productManagement.services.impl.ProductServiceImpl
import com.edgar.commonlibrary.dtos.ProductDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ProductController(private val productServiceImpl: ProductServiceImpl) {

    @GetMapping
    fun helloWorld() : ResponseEntity<String> {
        return ResponseEntity("Hello, World from Management", HttpStatus.OK)
    }

    @GetMapping("/products")
    fun getAllProducts() : ResponseEntity<List<ProductDto>> {
        return ResponseEntity.ok(productServiceImpl.getAllProducts())
    }

    @GetMapping("/products/{id}")
    fun getProductById(@PathVariable("id") id : Long) : ResponseEntity<ProductDto> {
        return ResponseEntity.ok(productServiceImpl.getProductById(id))
    }

    @PostMapping("/products")
    fun createProduct(
        @Valid
        @RequestBody
        addProductDto: AddProductDto
    ) : ResponseEntity<String> {
        productServiceImpl.addProduct(addProductDto)
        return ResponseEntity.ok("Ok")
    }
}