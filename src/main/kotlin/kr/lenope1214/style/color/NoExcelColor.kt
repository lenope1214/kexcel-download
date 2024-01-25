package kr.lenope1214.style.color

import org.apache.poi.ss.usermodel.CellStyle

class NoExcelColor : ExcelColor {
    override fun applyForeground(cellStyle: CellStyle) {
        // Do nothing
    }
}
