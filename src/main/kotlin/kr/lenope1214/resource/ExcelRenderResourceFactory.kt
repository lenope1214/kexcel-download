package kr.lenope1214.resource

import kr.lenope1214.DefaultBodyStyle
import kr.lenope1214.DefaultHeaderStyle
import kr.lenope1214.ExcelColumn
import kr.lenope1214.ExcelColumnStyle
import kr.lenope1214.exception.InvalidExcelCellStyleException
import kr.lenope1214.exception.NoExcelColumnAnnotationsException
import kr.lenope1214.resource.ExcelCellKey.Companion.of
import kr.lenope1214.resource.collection.PreCalculatedCellStyleMap
import kr.lenope1214.style.ExcelCellStyle
import kr.lenope1214.style.NoExcelCellStyle
import kr.lenope1214.utils.getAllFields
import kr.lenope1214.utils.getAnnotation
import org.apache.poi.ss.usermodel.Workbook

object ExcelRenderResourceFactory {

    fun prepareRenderResource(
        type: Class<*>,
        wb: Workbook,
        dataFormatDecider: DataFormatDecider,
    ): ExcelRenderResource {
        val styleMap = PreCalculatedCellStyleMap(dataFormatDecider)
        val headerNamesMap: MutableMap<String, String> = LinkedHashMap()
        val fieldNames: MutableList<String> = ArrayList()

        val classDefinedHeaderStyle = getHeaderExcelColumnStyle(type)
        val classDefinedBodyStyle = getBodyExcelColumnStyle(type)

        // 필드별로 루프를 돌면서 @ExcelColumn이 있는지 확인
        for (field in getAllFields(type)) {
            // @ExcelColumn이 있으면
            if (field.isAnnotationPresent(ExcelColumn::class.java)) {

                // @ExcelColumn 어노테이션을 가져온다
                val annotation = field.getAnnotation(ExcelColumn::class.java)
                val headerCellStyle = getCellStyle(decideAppliedStyleAnnotation(classDefinedHeaderStyle, annotation.headerStyle))
                styleMap.put(
                    String::class.java, // 헤더는 문자열 고정
                    of(field.name, ExcelRenderLocation.HEADER),
                    headerCellStyle,
                    wb,
                )
                val fieldType = field.type

                val bodyCellStyle = getCellStyle(decideAppliedStyleAnnotation(classDefinedBodyStyle, annotation.bodyStyle))
                styleMap.put(
                    fieldType,
                    of(field.name, ExcelRenderLocation.BODY),
                    bodyCellStyle,
                    wb,
                )
                fieldNames.add(field.name)
                headerNamesMap[field.name] = annotation.headerName
            }
        }

        if (styleMap.isEmpty) {
            throw NoExcelColumnAnnotationsException(String.format("Class %s has not @ExcelColumn at all", type))
        }
        return ExcelRenderResource(styleMap, headerNamesMap, fieldNames)
    }

    private fun getHeaderExcelColumnStyle(clazz: Class<*>): ExcelColumnStyle? {
        val annotation = getAnnotation(clazz, DefaultHeaderStyle::class.java) ?: return null
        return (annotation as DefaultHeaderStyle).style
    }

    private fun getBodyExcelColumnStyle(clazz: Class<*>): ExcelColumnStyle? {
        val annotation = getAnnotation(clazz, DefaultBodyStyle::class.java) ?: return null
        return (annotation as DefaultBodyStyle).style
    }

    private fun decideAppliedStyleAnnotation(
        classAnnotation: ExcelColumnStyle?,
        fieldAnnotation: ExcelColumnStyle,
    ): ExcelColumnStyle {
        if (fieldAnnotation.excelCellStyleClass == NoExcelCellStyle::class && classAnnotation != null) {
            return classAnnotation
        }
        return fieldAnnotation
    }

    private fun getCellStyle(excelColumnStyle: ExcelColumnStyle): ExcelCellStyle {
        val excelCellStyleClass: Class<out ExcelCellStyle> = excelColumnStyle.excelCellStyleClass.java
        // 1. Case of Enum
        if (excelCellStyleClass.isEnum) {
            val enumName = excelColumnStyle.enumName
            return findExcelCellStyle(excelCellStyleClass, enumName)
        }

        // 2. Case of Class
        try {
            return excelCellStyleClass.getDeclaredConstructor().newInstance()
//            return excelCellStyleClass.newInstance() // deprecated since Java 9
        } catch (e: NoSuchMethodException) {
            throw InvalidExcelCellStyleException(e.message, e)
        } catch (e: InstantiationException) {
            throw InvalidExcelCellStyleException(e.message, e)
        } catch (e: IllegalAccessException) {
            throw InvalidExcelCellStyleException(e.message, e)
        }
    }

    fun findExcelCellStyle(
        excelCellStyles: Class<out ExcelCellStyle>,
        enumName: String,
    ): ExcelCellStyle {
        return try {
            excelCellStyles.nestMembers.find {
                it.name == enumName
            } as ExcelCellStyle? ?: throw IllegalArgumentException()
        } catch (e: NullPointerException) {
            throw InvalidExcelCellStyleException("enumName must not be null", e)
        } catch (e: IllegalArgumentException) {
            throw InvalidExcelCellStyleException(
                String.format("Enum %s does not name %s", excelCellStyles.getName(), enumName), e
            )
        }
    }
}
