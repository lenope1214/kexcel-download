package kr.lenope1214.style.configurer

import kr.lenope1214.style.align.ExcelAlign
import kr.lenope1214.style.align.NoExcelAlign
import kr.lenope1214.style.border.ExcelBorders
import kr.lenope1214.style.border.NoExcelBorders
import kr.lenope1214.style.color.DefaultExcelColor
import kr.lenope1214.style.color.ExcelColor
import kr.lenope1214.style.color.NoExcelColor
import org.apache.poi.ss.usermodel.CellStyle

class ExcelCellStyleConfigurer {
    private var excelAlign: ExcelAlign = NoExcelAlign()
    private var foregroundColor: ExcelColor = NoExcelColor()
    private var excelBorders: ExcelBorders = NoExcelBorders()

    fun excelAlign(excelAlign: ExcelAlign): ExcelCellStyleConfigurer {
        this.excelAlign = excelAlign
        return this
    }

    fun foregroundColor(
        red: Int,
        blue: Int,
        green: Int
    ): ExcelCellStyleConfigurer {
        this.foregroundColor = DefaultExcelColor.rgb(red, blue, green)
        return this
    }

    fun excelBorders(excelBorders: ExcelBorders): ExcelCellStyleConfigurer {
        this.excelBorders = excelBorders
        return this
    }

    fun configure(cellStyle: CellStyle) {
        excelAlign.apply(cellStyle)
        foregroundColor.applyForeground(cellStyle)
        excelBorders.apply(cellStyle)
    }
}
