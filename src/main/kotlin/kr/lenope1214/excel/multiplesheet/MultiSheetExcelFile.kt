package kr.lenope1214.excel.multiplesheet

import kr.lenope1214.excel.SXSSFExcelFile
import kr.lenope1214.resource.DataFormatDecider
import org.apache.commons.compress.archivers.zip.Zip64Mode

/**
 * MultiSheetExcelFile
 *
 * - support Excel Version over 2007
 * - support multi sheet rendering
 * - support Dffierent DataFormat by Class Type
 * - support Custom CellStyle according to (header or body) and data field
 */
class MultiSheetExcelFile<T : Any> : SXSSFExcelFile<T> {
    private var currentRowIndex = ROW_START_INDEX

    constructor(type: Class<T>) : super(type) {
        wb.setZip64Mode(Zip64Mode.Always)
    }

    /*
	 * If you use SXSSF with hug data, you need to set zip mode
	 * see http://apache-poi.1045710.n5.nabble.com/Bug-62872-New-Writing-large-files-with-800k-rows-gives-java-io-IOException-This-archive-contains-unc-td5732006.html
	 */
    constructor(
        data: List<T>,
        type: Class<T>
    ) : super(data, type) {
        wb.setZip64Mode(Zip64Mode.Always)
    }

    constructor(
        data: List<T>,
        type: Class<T>,
        dataFormatDecider: DataFormatDecider
    ) : super(data, type, dataFormatDecider) {
        wb.setZip64Mode(Zip64Mode.Always)
    }

    override fun renderExcel(data: List<T>) {
        // 1. Create header and return if data is empty
        if (data.isEmpty()) {
            createNewSheetWithHeader()
            return
        }

        // 2. Render body
        createNewSheetWithHeader()
        addRows(data)
    }

   override fun addRows(data: List<T>) {
        for (renderedData in data) {
            renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX)
            if (currentRowIndex == maxRowCanBeRendered) {
                currentRowIndex = 1
                createNewSheetWithHeader()
            }
        }
    }

    private fun createNewSheetWithHeader() {
        sheet = wb.createSheet()
        renderHeadersWithNewSheet(sheet!!, ROW_START_INDEX, COLUMN_START_INDEX)
        currentRowIndex++
    }

    companion object {
        private val maxRowCanBeRendered: Int = supplyExcelVersion.maxRows - 1
        private const val ROW_START_INDEX = 0
        private const val COLUMN_START_INDEX = 0
    }

}
