package kr.lenope1214.resource

import kr.lenope1214.dto.ExcelDto
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFColor
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
        assertThat(cellStyle.alignment).isEqualTo(HorizontalAlignment.CENTER)
        assertThat(cellStyle.verticalAlignment).isEqualTo(VerticalAlignment.CENTER)
        assertThat(cellStyle.borderTop).isEqualTo(BorderStyle.THIN)
        assertThat(cellStyle.borderRight).isEqualTo(BorderStyle.THIN)
        assertThat(cellStyle.borderLeft).isEqualTo(BorderStyle.THIN)
        assertThat(cellStyle.borderBottom).isEqualTo(BorderStyle.THIN)
        val nameHeaderCellColor: XSSFColor = cellStyle.fillForegroundColorColor as XSSFColor
        assertThat(nameHeaderCellColor.getRGB()).isEqualTo(arrayOf<Byte>(red, green, blue))
    }
}
