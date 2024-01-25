package kr.lenope1214.resource

import org.apache.poi.ss.usermodel.DataFormat
import java.util.Arrays

class DefaultDataFormatDecider : DataFormatDecider {
    override fun getDataFormat(
        dataFormat: DataFormat,
        type: Class<*>
    ): Short {
        if (isFloatType(type)) {
            return dataFormat.getFormat(FLOAT_FORMAT_2_DECIMAL_PLACES)
        }

        if (isIntegerType(type)) {
            return dataFormat.getFormat(CURRENT_FORMAT)
        }
        return dataFormat.getFormat(DEFAULT_FORMAT)
    }

    private fun isFloatType(type: Class<*>): Boolean {
        val floatTypes = listOf<Class<*>?>(
            Float::class.java, Float::class.javaPrimitiveType,
            Double::class.java, Double::class.javaPrimitiveType
        )
        return floatTypes.contains(type)
    }

    private fun isIntegerType(type: Class<*>): Boolean {
        val integerTypes = listOf<Class<*>?>(
            Byte::class.java, Byte::class.javaPrimitiveType,
            Short::class.java, Short::class.javaPrimitiveType,
            Int::class.java, Int::class.javaPrimitiveType,
            Long::class.java, Long::class.javaPrimitiveType
        )
        return integerTypes.contains(type)
    }

    companion object {
        private const val CURRENT_FORMAT = "#,##0"
        private const val FLOAT_FORMAT_2_DECIMAL_PLACES = "#,##0.00"
        private const val DEFAULT_FORMAT = ""
    }
}
