package kr.lenope1214.style.border

import org.apache.poi.ss.usermodel.CellStyle

interface ExcelBorder {
    fun applyTop(cellStyle: CellStyle)

    fun applyRight(cellStyle: CellStyle)

    fun applyBottom(cellStyle: CellStyle)

    fun applyLeft(cellStyle: CellStyle)
}
