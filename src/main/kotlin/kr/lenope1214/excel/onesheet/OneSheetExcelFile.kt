package kr.lenope1214.excel.onesheet

import kr.lenope1214.excel.SXSSFExcelFile
import kr.lenope1214.resource.DataFormatDecider

/**
 * OneSheetExcelFile
 *
 * - support Excel Version over 2007
 * - support one sheet rendering
 * - support different DataFormat by Class Type
 * - support Custom CellStyle according to (header or body) and data field
 */
class OneSheetExcelFile<T: Any> : SXSSFExcelFile<T> {
    private var currentRowIndex = ROW_START_INDEX

    constructor(type: Class<T>) : super(type)

    constructor(
        data: List<T>,
        type: Class<T>
    ) : super(data, type)

    constructor(
        data: List<T>,
        type: Class<T>,
        dataFormatDecider: DataFormatDecider
    ) : super(data, type, dataFormatDecider)

    override fun validateData(data: List<T>) {
        val maxRows: Int = supplyExcelVersion.maxRows
        require(data.size <= maxRows) { String.format("This concrete ExcelFile does not support over %s rows", maxRows) }
    }

    override fun renderExcel(data: List<T>) {
        // 1. Create sheet and renderHeader
        sheet = wb.createSheet()
        renderHeadersWithNewSheet(sheet!!, currentRowIndex++, COLUMN_START_INDEX)

        if (data.isEmpty()) {
            return
        }

        // 2. Render Body
        for (renderedData in data) {
            renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX)
        }
    }

    override fun addRows(data: List<T>) {
        renderBody(data, currentRowIndex++, COLUMN_START_INDEX)
    }

    companion object {
        private const val ROW_START_INDEX = 0
        private const val COLUMN_START_INDEX = 0
    }
}
