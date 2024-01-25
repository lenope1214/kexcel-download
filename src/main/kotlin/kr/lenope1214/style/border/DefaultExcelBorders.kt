package kr.lenope1214.style.border

import org.apache.poi.ss.usermodel.CellStyle

class DefaultExcelBorders(borders: List<ExcelBorder?>) : ExcelBorders {
    private val borders: List<ExcelBorder?>

    init {
        validateBorders(borders)
        this.borders = borders
    }

    private fun validateBorders(borders: List<ExcelBorder?>) {
        require(borders.size == 4) { "Should be initialized with TOP RIGHT LEFT BOTTOM borders" }
    }

    override fun apply(cellStyle: CellStyle) {
        borders[0]!!.applyTop(cellStyle)
        borders[1]!!.applyRight(cellStyle)
        borders[2]!!.applyBottom(cellStyle)
        borders[3]!!.applyLeft(cellStyle)
    }

    companion object {
        fun newInstance(style: ExcelBorderStyle): DefaultExcelBorders {
            val excelBorders: MutableList<DefaultExcelBorder?> = ArrayList()
            for (i in 0 .. 3) {
                excelBorders.add(DefaultExcelBorder(style))
            }
            return DefaultExcelBorders(excelBorders)
        }
    }
}
