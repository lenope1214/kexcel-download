package kr.lenope1214.resource

import com.lannstark.resource.collection.PreCalculatedCellStyleMap

class ExcelRenderResource(
    styleMap: PreCalculatedCellStyleMap,
    // TODO dataFieldName -> excelHeaderName Map Abstraction
    private val excelHeaderNames: Map<String, String>,
    val dataFieldNames: List<String>
) {
    private val styleMap: PreCalculatedCellStyleMap = styleMap

    fun getCellStyle(
        dataFieldName: String,
        excelRenderLocation: ExcelRenderLocation?
    ): CellStyle {
        return styleMap.get(ExcelCellKey.Companion.of(dataFieldName, excelRenderLocation))
    }

    fun getExcelHeaderName(dataFieldName: String): String? {
        return excelHeaderNames[dataFieldName]
    }
}
