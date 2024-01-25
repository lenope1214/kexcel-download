package kr.lenope1214.resource.collection

import kr.lenope1214.resource.DataFormatDecider
import kr.lenope1214.resource.ExcelCellKey
import kr.lenope1214.style.ExcelCellStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.DataFormat
import org.apache.poi.ss.usermodel.Workbook

/**
 * PreCalculatedCellStyleMap
 *
 * Determines cell's style
 * In currently, PreCalculatedCellStyleMap determines {org.apache.poi.ss.usermodel.DataFormat}
 *
 */
class PreCalculatedCellStyleMap(
    private val dataFormatDecider: DataFormatDecider,
) {

    private val cellStyleMap: MutableMap<ExcelCellKey, CellStyle> = HashMap<ExcelCellKey, CellStyle>()

    fun put(
        fieldType: Class<*>,
        excelCellKey: ExcelCellKey,
        excelCellStyle: ExcelCellStyle,
        wb: Workbook
    ) {
        val cellStyle: CellStyle = wb.createCellStyle()
        val dataFormat: DataFormat = wb.createDataFormat()
        cellStyle.dataFormat = dataFormatDecider.getDataFormat(dataFormat, fieldType)
        excelCellStyle.apply(cellStyle)
        cellStyleMap[excelCellKey] = cellStyle
    }

    fun get(excelCellKey: ExcelCellKey): CellStyle {
        return cellStyleMap[excelCellKey] ?: throw IllegalStateException("CellStyle is not found")
    }

    val isEmpty: Boolean
        get() = cellStyleMap.isEmpty()
}
