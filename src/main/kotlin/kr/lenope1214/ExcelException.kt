package kr.lenope1214

open class ExcelException(
    message: String? = "",
    cause: Throwable? = null,
) : RuntimeException(message, cause)
