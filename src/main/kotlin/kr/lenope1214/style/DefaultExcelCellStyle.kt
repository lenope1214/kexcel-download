package kr.lenope1214.style

import kr.lenope1214.style.align.DefaultExcelAlign
import kr.lenope1214.style.align.ExcelAlign
import kr.lenope1214.style.border.DefaultExcelBorders
import kr.lenope1214.style.border.ExcelBorderStyle
import kr.lenope1214.style.color.DefaultExcelColor
import kr.lenope1214.style.color.ExcelColor
import org.apache.poi.ss.usermodel.CellStyle

/**
 * Example of using ExcelCellStyle as Enum
 */
enum class DefaultExcelCellStyle(
    private val backgroundColor: ExcelColor,
    /**
     * like CSS margin or padding rule,
     * List<DefaultExcelBorder> represents rgb TOP RIGHT BOTTOM LEFT
    </DefaultExcelBorder> */
    private val borders: DefaultExcelBorders,
    private val align: ExcelAlign
) : ExcelCellStyle {
    GREY_HEADER(
        DefaultExcelColor.rgb(217, 217, 217),
        DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER
    ),
    BLUE_HEADER(
        DefaultExcelColor.rgb(223, 235, 246),
        DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER
    ),
    BODY(
        DefaultExcelColor.rgb(255, 255, 255),
        DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.RIGHT_CENTER
    );

    override fun apply(cellStyle: CellStyle) {
        backgroundColor.applyForeground(cellStyle)
        borders.apply(cellStyle)
        align.apply(cellStyle)
    }
}
