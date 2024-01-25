package kr.lenope1214.exception

import com.lannstark.ExcelException

class InvalidExcelCellStyleException(
    message: String?,
    cause: Throwable?
) : ExcelException(message, cause)
