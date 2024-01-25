package kr.lenope1214.resource

import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExcelRenderResourceFactoryTest {
    @Test
    fun excelRenderResourceCreationTest() {
        // given & when
        val resource = ExcelRenderResourceFactory.prepareRenderResource(ExcelDto::class.java, SXSSFWorkbook(), DefaultDataFormatDecider())

        // then
        assertThat(resource.dataFieldNames).isEqualTo(mutableListOf("name", "age"))

        assertCenterThinCellStyle(resource.getCellStyle("name", ExcelRenderLocation.HEADER), 223.toByte(), 235.toByte(), 246.toByte())
        assertCenterThinCellStyle(resource.getCellStyle("age", ExcelRenderLocation.HEADER), 0.toByte(), 0.toByte(), 0.toByte())
    }

    private fun assertCenterThinCellStyle(
        cellStyle: CellStyle,
        red: Byte,
        green: Byte,
        blue: Byte
    ) {
        assertThat(cellStyle.getAlignment()).isEqualTo(HorizontalAlignment.CENTER)
        assertThat(cellStyle.getVerticalAlignment()).isEqualTo(VerticalAlignment.CENTER)
        assertThat(cellStyle.getBorderTop()).isEqualTo(BorderStyle.THIN)
        assertThat(cellStyle.getBorderRight()).isEqualTo(BorderStyle.THIN)
        assertThat(cellStyle.getBorderLeft()).isEqualTo(BorderStyle.THIN)
        assertThat(cellStyle.getBorderBottom()).isEqualTo(BorderStyle.THIN)
        val nameHeaderCellColor: XSSFColor = cellStyle.getFillForegroundColorColor() as XSSFColor
        assertThat(nameHeaderCellColor.getRGB()).isEqualTo(arrayOf<Byte>(red, green, blue))
    }
}
