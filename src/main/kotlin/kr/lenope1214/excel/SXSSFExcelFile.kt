package kr.lenope1214.excel

import com.lannstark.exception.ExcelInternalException
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import java.io.OutputStream
import java.lang.reflect.Field

abstract class SXSSFExcelFile<T> @JvmOverloads constructor(
    data: List<T>?,
    type: Class<T>?,
    dataFormatDecider: DataFormatDecider? = DefaultDataFormatDecider()
) : ExcelFile<T> {
    protected var wb: SXSSFWorkbook
    protected var sheet: Sheet? = null
    protected var resource: ExcelRenderResource

    /**
     * SXSSFExcelFile
     * @param type Class type to be rendered
     */
    constructor(type: Class<T>?) : this(emptyList<T>(), type, DefaultDataFormatDecider())

    /**
     * SXSSFExcelFile
     * @param data List Data to render excel file. data should have at least one @ExcelColumn on fields
     * @param type Class type to be rendered
     * @param dataFormatDecider Custom DataFormatDecider
     */
    /**
     * SXSSFExcelFile
     * @param data List Data to render excel file. data should have at least one @ExcelColumn on fields
     * @param type Class type to be rendered
     */
    init {
        validateData(data)
        this.wb = SXSSFWorkbook()
        this.resource = ExcelRenderResourceFactory.prepareRenderResource(type, wb, dataFormatDecider)
        renderExcel(data)
    }

    protected fun validateData(data: List<T>?) {}

    protected abstract fun renderExcel(data: List<T>?)

    protected fun renderHeadersWithNewSheet(
        sheet: Sheet,
        rowIndex: Int,
        columnStartIndex: Int
    ) {
        val row = sheet.createRow(rowIndex)
        var columnIndex = columnStartIndex
        for (dataFieldName in resource.getDataFieldNames()) {
            val cell = row.createCell(columnIndex++)
            cell.cellStyle = resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER)
            cell.setCellValue(resource.getExcelHeaderName(dataFieldName))
        }
    }

    protected fun renderBody(
        data: Any,
        rowIndex: Int,
        columnStartIndex: Int
    ) {
        val row = sheet!!.createRow(rowIndex)
        var columnIndex = columnStartIndex
        for (dataFieldName in resource.getDataFieldNames()) {
            val cell = row.createCell(columnIndex++)
            try {
                val field: Field = getField(data.javaClass, (dataFieldName))
                field.isAccessible = true
                cell.cellStyle = resource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY)
                val cellValue = field[data]
                renderCellValue(cell, cellValue)
            } catch (e: Exception) {
                throw ExcelInternalException(e.message, e)
            }
        }
    }

    private fun renderCellValue(
        cell: Cell,
        cellValue: Any?
    ) {
        if (cellValue is Number) {
            cell.setCellValue(cellValue.toDouble())
            return
        }
        cell.setCellValue(cellValue?.toString() ?: "")
    }

    @Throws(IOException::class)
    override fun write(stream: OutputStream) {
        wb.write(stream)
        wb.close()
        wb.dispose()
        stream.close()
    }

    companion object {
        protected val supplyExcelVersion: SpreadsheetVersion = SpreadsheetVersion.EXCEL2007
    }
}
