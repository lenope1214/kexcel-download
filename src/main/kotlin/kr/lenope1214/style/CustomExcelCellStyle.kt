package kr.lenope1214.style

import kr.lenope1214.style.configurer.ExcelCellStyleConfigurer
import org.apache.poi.ss.usermodel.CellStyle

abstract class CustomExcelCellStyle : ExcelCellStyle {
    private val configurer: ExcelCellStyleConfigurer = ExcelCellStyleConfigurer()

    init {
        configure(configurer)
    }

    abstract fun configure(configurer: ExcelCellStyleConfigurer)

    override fun apply(cellStyle: CellStyle) {
        configurer.configure(cellStyle)
    }
}
