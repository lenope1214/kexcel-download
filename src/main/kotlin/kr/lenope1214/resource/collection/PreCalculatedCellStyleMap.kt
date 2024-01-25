package kr.lenope1214.resource.collection

import com.lannstark.resource.DataFormatDecider

/**
 * PreCalculatedCellStyleMap
 *
 * Determines cell's style
 * In currently, PreCalculatedCellStyleMap determines {org.apache.poi.ss.usermodel.DataFormat}
 *
 */
class PreCalculatedCellStyleMap(dataFormatDecider: DataFormatDecider) {
    private val dataFormatDecider: DataFormatDecider = dataFormatDecider

    private val cellStyleMap: MutableMap<ExcelCellKey, CellStyle> = HashMap<ExcelCellKey, CellStyle>()

    fun put(
        fieldType: Class<*>?,
        excelCellKey: ExcelCellKey,
        excelCellStyle: ExcelCellStyle,
        wb: Workbook
    ) {
        val cellStyle: CellStyle = wb.createCellStyle()
        val dataFormat: DataFormat = wb.createDataFormat()
        cellStyle.setDataFormat(dataFormatDecider.getDataFormat(dataFormat, fieldType))
        excelCellStyle.apply(cellStyle)
        cellStyleMap[excelCellKey] = cellStyle
    }

    fun get(excelCellKey: ExcelCellKey): CellStyle? {
        return cellStyleMap[excelCellKey]
    }

    val isEmpty: Boolean
        get() = cellStyleMap.isEmpty()
}
