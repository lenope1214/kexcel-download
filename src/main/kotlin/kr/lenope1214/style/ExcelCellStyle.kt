package kr.lenope1214.style

import org.apache.poi.ss.usermodel.CellStyle

interface ExcelCellStyle {
    fun apply(cellStyle: CellStyle)
}
