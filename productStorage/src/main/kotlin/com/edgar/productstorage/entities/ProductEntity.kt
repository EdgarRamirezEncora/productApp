package com.edgar.productstorage.entities

import jakarta.persistence.*
import lombok.AllArgsConstructor
import java.math.BigDecimal
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal
)