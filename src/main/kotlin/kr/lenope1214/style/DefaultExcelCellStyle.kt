package kr.lenope1214.style

import com.lannstark.style.align.DefaultExcelAlign

/**
 * Example of using ExcelCellStyle as Enum
 */
enum class DefaultExcelCellStyle(
    backgroundColor: ExcelColor,
    borders: DefaultExcelBorders,
    align: ExcelAlign
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

    private val backgroundColor: ExcelColor = backgroundColor

    /**
     * like CSS margin or padding rule,
     * List<DefaultExcelBorder> represents rgb TOP RIGHT BOTTOM LEFT
    </DefaultExcelBorder> */
    private val borders: DefaultExcelBorders = borders
    private val align: ExcelAlign = align

    override fun apply(cellStyle: CellStyle?) {
        backgroundColor.applyForeground(cellStyle)
        borders.apply(cellStyle)
        align.apply(cellStyle)
    }
}
