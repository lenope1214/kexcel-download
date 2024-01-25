package kr.lenope1214.resource

import kr.lenope1214.resource.collection.PreCalculatedCellStyleMap
import org.apache.poi.ss.usermodel.CellStyle

class ExcelRenderResource(
    private val styleMap: PreCalculatedCellStyleMap,
    // TODO dataFieldName -> excelHeaderName Map Abstraction
    private val excelHeaderNames: Map<String, String>,
    val dataFieldNames: List<String>
) {

    fun getCellStyle(
        dataFieldName: String,
        excelRenderLocation: ExcelRenderLocation
    ): CellStyle {
        return styleMap.get(ExcelCellKey.of(dataFieldName, excelRenderLocation))
    }

    fun getExcelHeaderName(dataFieldName: String): String? {
        return excelHeaderNames[dataFieldName]
    }
}
