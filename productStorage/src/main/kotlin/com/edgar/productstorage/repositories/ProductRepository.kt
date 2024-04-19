package com.edgar.productstorage.repositories

import com.edgar.productstorage.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal


@Repository
interface ProductRepository : JpaRepository<ProductEntity, BigDecimal> {
    /**
     * Get the list of products from the database using the GetAllProductsFn function
     * in the database.
     * @return the list of products in the database.
     */
    @Query(value = "SELECT * FROM GetAllProductsFn();", nativeQuery = true)
    fun getAllProducts(): List<ProductEntity>

    /**
     * Add a product in the database using the stored procedure called AddProductSp.
     * @param name the name of the product.
     * @param description the description of the product.
     * @param price the price of the product.
     */
    @Modifying
    @Transactional
    @Query(value = "CALL AddProductSp(:name, :description, :price);", nativeQuery = true)
    fun addProduct(
        @Param("name") name: String,
        @Param("description") description: String,
        @Param("price") price: BigDecimal
    )

    /**
     * Get a product from the database using the GetAllProductFn function
     * in the database.
     * @param id the id of the product.
     * @return the list of products in the database.
     */
    @Query(value = "SELECT * FROM GetProductFn(:id);", nativeQuery = true)
    fun getProductById(id: Long): ProductEntity?
}
