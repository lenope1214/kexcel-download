package kr.lenope1214

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultHeaderStyle(val style: ExcelColumnStyle)
