package com.edgar.commonlibrary.util

class ErrorMessages {
    companion object {
        const val GENERIC_INVALID_PRODUCT_DATA = "Invalid product data!"
        const val INVALID_PRODUCT_NAME_MESSAGE = "Product name must not be null or empty."
        const val NULL_PRODUCT_PRICE_MESSAGE = "Product price must not be null."
        const val NEGATIVE_PRODUCT_PRICE_MESSAGE = "Product price must be positive."
        const val INVALID_PRODUCT_DESCRIPTION_MESSAGE = "Product description must not be null or empty."
        const val PRODUCT_NOT_FOUND_MESSAGE = "Product not found!"
        const val GENERIC_MESSAGE_QUEUE_ERROR_MESSAGE = "There was a problem trying to connect to the message queue."
        const val GENERIC_ERROR_MESSAGE = "Something went wrong! Try again later."
        const val INVALID_PRODUCT_LIST_MESSAGE = "There was a problem trying to list of products."
        const val INVALID_PRODUCT_MESSAGE = "There was a problem trying to get the product."
    }
}