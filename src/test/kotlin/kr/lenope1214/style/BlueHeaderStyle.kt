package kr.lenope1214.style

import kr.lenope1214.style.align.DefaultExcelAlign
import kr.lenope1214.style.border.DefaultExcelBorders
import kr.lenope1214.style.border.ExcelBorderStyle
import kr.lenope1214.style.configurer.ExcelCellStyleConfigurer

class BlueHeaderStyle : CustomExcelCellStyle() {
    override fun configure(configurer: ExcelCellStyleConfigurer?) {
        configurer!!.foregroundColor(223, 235, 246)
            .excelBorders(DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN))
            .excelAlign(DefaultExcelAlign.CENTER_CENTER)
    }
}
