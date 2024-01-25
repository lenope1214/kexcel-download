package kr.lenope1214.exception

import kr.lenope1214.ExcelException

class UnSupportedExcelTypeException(
    message: String? = "",
    cause: Throwable? = null,
) : ExcelException(message, cause)
