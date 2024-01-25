package kr.lenope1214.style

import com.lannstark.style.configurer.ExcelCellStyleConfigurer

abstract class CustomExcelCellStyle : ExcelCellStyle {
    private val configurer: ExcelCellStyleConfigurer = ExcelCellStyleConfigurer()

    init {
        configure(configurer)
    }

    abstract fun configure(configurer: ExcelCellStyleConfigurer?)

    override fun apply(cellStyle: CellStyle?) {
        configurer.configure(cellStyle)
    }
}
