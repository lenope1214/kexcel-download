package kr.lenope1214.style.border

import org.apache.poi.ss.usermodel.CellStyle

interface ExcelBorders {
    fun apply(cellStyle: CellStyle)
}
