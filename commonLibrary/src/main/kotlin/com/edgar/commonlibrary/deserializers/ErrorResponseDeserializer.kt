package com.edgar.commonlibrary.deserializers

import com.edgar.commonlibrary.util.ErrorResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ErrorResponseDeserializer {
    companion object {
        private val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    }

    fun deserializeErrorResponse(response: Any?): ErrorResponse {
        return mapper.convertValue(response, ErrorResponse::class.java)
    }
}