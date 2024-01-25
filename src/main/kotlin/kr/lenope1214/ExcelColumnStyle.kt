package kr.lenope1214

import kr.lenope1214.style.ExcelCellStyle
import kotlin.reflect.KClass

annotation class ExcelColumnStyle(
    /**
     * Enum implements [ExcelCellStyle]
     * Also, can use just class.
     * If not use Enum, enumName will be ignored
     * @see kr.lenope1214.style.DefaultExcelCellStyle
     *
     * @see kr.lenope1214.style.CustomExcelCellStyle
     */
    val excelCellStyleClass: KClass<out ExcelCellStyle>,
    /**
     * name of Enum implements [ExcelCellStyle]
     * if not use Enum, enumName will be ignored
     */
    val enumName: String = ""
)
