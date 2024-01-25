package kr.lenope1214

import kr.lenope1214.style.NoExcelCellStyle

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelColumn(
    val headerName: String = "",
    val headerStyle: ExcelColumnStyle = ExcelColumnStyle(excelCellStyleClass = NoExcelCellStyle::class),
    val bodyStyle: ExcelColumnStyle = ExcelColumnStyle(excelCellStyleClass = NoExcelCellStyle::class)
)
