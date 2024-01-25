package kr.lenope1214.style

import kr.lenope1214.style.align.DefaultExcelAlign
import kr.lenope1214.style.border.DefaultExcelBorders
import kr.lenope1214.style.border.ExcelBorderStyle
import kr.lenope1214.style.configurer.ExcelCellStyleConfigurer

class BlackHeaderStyle : CustomExcelCellStyle() {
    override fun configure(configurer: ExcelCellStyleConfigurer?) {
        configurer!!.foregroundColor(0, 0, 0)
            .excelBorders(DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN))
            .excelAlign(DefaultExcelAlign.CENTER_CENTER)
    }
}
