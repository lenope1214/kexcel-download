package kr.lenope1214.style.border

import org.apache.poi.ss.usermodel.CellStyle

class DefaultExcelBorder(private val borderStyle: ExcelBorderStyle) : ExcelBorder {
    override fun applyTop(cellStyle: CellStyle) {
        cellStyle.borderTop = borderStyle.style
    }

    override fun applyRight(cellStyle: CellStyle) {
        cellStyle.borderRight = borderStyle.style
    }

    override fun applyBottom(cellStyle: CellStyle) {
        cellStyle.borderBottom = borderStyle.style
    }

    override fun applyLeft(cellStyle: CellStyle) {
        cellStyle.borderLeft = borderStyle.style
    }
}
