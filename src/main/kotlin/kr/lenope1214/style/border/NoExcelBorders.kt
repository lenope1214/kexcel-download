package kr.lenope1214.style.border

import org.apache.poi.ss.usermodel.CellStyle

class NoExcelBorders : ExcelBorders {
    override fun apply(cellStyle: CellStyle) {
        // Do nothing
    }
}
