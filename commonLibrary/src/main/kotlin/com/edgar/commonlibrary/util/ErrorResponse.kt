package com.edgar.commonlibrary.util

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.util.Date

@NoArgsConstructor
@AllArgsConstructor
data class ErrorResponse(
    var message: String,
    var statusCode: Int,
    var date: Date
)