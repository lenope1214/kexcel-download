package kr.lenope1214.exception

import com.lannstark.ExcelException

class ExcelInternalException(
    message: String?,
    cause: Throwable?
) : ExcelException(message, cause)
