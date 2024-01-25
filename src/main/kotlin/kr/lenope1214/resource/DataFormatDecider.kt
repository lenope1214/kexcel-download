package kr.lenope1214.resource

import org.apache.poi.ss.usermodel.DataFormat

interface DataFormatDecider {
    fun getDataFormat(
        dataFormat: DataFormat,
        type: Class<*>?
    ): Short
}
