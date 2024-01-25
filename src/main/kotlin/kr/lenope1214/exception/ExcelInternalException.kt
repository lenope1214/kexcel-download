package kr.lenope1214.exception

import kr.lenope1214.ExcelException

class ExcelInternalException(
    message: String? = "",
    cause: Throwable? = null,
) : ExcelException(message, cause)
