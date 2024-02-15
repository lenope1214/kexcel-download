package kr.lenope1214.excel

import kr.lenope1214.exception.ExcelInternalException
import kr.lenope1214.resource.DataFormatDecider
import kr.lenope1214.resource.DefaultDataFormatDecider
import kr.lenope1214.resource.ExcelRenderLocation
import kr.lenope1214.resource.ExcelRenderResource
import kr.lenope1214.resource.ExcelRenderResourceFactory
import kr.lenope1214.utils.getField
import org.apache.poi.ss.SpreadsheetVersion
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.IOException
import java.io.OutputStream
import java.lang.reflect.Field

abstract class SXSSFExcelFile<T> constructor(
    data: List<T>,
    type: Class<T>,
    dataFormatDecider: DataFormatDecider = DefaultDataFormatDecider(),
    val headerNameMap: Map<String, String> = emptyMap(),
) : ExcelFile<T> {
    protected var wb: SXSSFWorkbook
    protected var sheet: Sheet? = null
    protected var resource: ExcelRenderResource

    /**
     * SXSSFExcelFile
     * @param type Class type to be rendered
     */
    constructor(type: Class<T>) : this(emptyList<T>(), type, DefaultDataFormatDecider())

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

    protected open fun validateData(data: List<T>) {}

    protected abstract fun renderExcel(data: List<T>)

    protected fun renderHeadersWithNewSheet(
        sheet: Sheet,
        rowIndex: Int,
        columnStartIndex: Int,
    ) {
        val row = sheet.createRow(rowIndex)
        var columnIndex = columnStartIndex
        for (dataFieldName in resource.dataFieldNames) {
            val cell = row.createCell(columnIndex++)
            cell.cellStyle = resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER)
            val cellValue = resource.getExcelHeaderName(dataFieldName)
            cell.setCellValue(headerNameMap[cellValue] ?: cellValue)
        }
    }

    protected fun <T : Any> renderBody(
        data: T,
        rowIndex: Int,
        columnStartIndex: Int,
    ) {
        val row = sheet!!.createRow(rowIndex)
        var columnIndex = columnStartIndex
        for (dataFieldName in resource.dataFieldNames) {
            val cell = row.createCell(columnIndex++)
            try {
                val field: Field = getField(data::class.java, (dataFieldName))
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
        cellValue: Any?,
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
        @JvmStatic
        protected val supplyExcelVersion: SpreadsheetVersion = SpreadsheetVersion.EXCEL2007
    }
}
