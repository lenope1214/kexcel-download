package kr.lenope1214.dto

import kr.lenope1214.DefaultHeaderStyle
import kr.lenope1214.ExcelColumn
import kr.lenope1214.ExcelColumnStyle
import kr.lenope1214.style.BlackHeaderStyle
import kr.lenope1214.style.BlueHeaderStyle

@DefaultHeaderStyle(style = ExcelColumnStyle(excelCellStyleClass = BlueHeaderStyle::class))
class ExcelDto {
    @ExcelColumn(headerName = "name")
    private val name: String? = null

    private val hideColumn: String? = null

    @ExcelColumn(headerName = "age", headerStyle = ExcelColumnStyle(excelCellStyleClass = BlackHeaderStyle::class))
    private val age = 0
}
