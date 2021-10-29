package common

import net.sourceforge.plantuml.FileFormat
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.SourceStringReader
import net.sourceforge.plantuml.cucadiagram.dot.GraphvizUtils
import java.io.File
import java.io.FileOutputStream

interface Diagram {
    fun buildPlantUmlString(): String

    fun exportResult(isSuccess: Boolean) = run { }

    infix fun export(filePath: String) {
        generateDiagram(filePath)
    }

    /**
     * skinparam backgroundColor transparent
     * skinparam defaultFontColor White
     * skinparam arrowFontColor Black
     * skinparam roundCorner 10
     * hide circle equals skinparam style strictuml
     * skinparam roundCorner 10
     **/
    fun getClassStyle(): String {
        return """
        |skinparam class {
        |   BorderColor black
        |   FontColor White
        |   AttributeFontColor White
        |   StereotypeFontColor White
        |}
        |skinparam defaultTextAlignment center
        |skinparam style strictuml
        |hide empty members
        """.trimIndent()
    }

    private fun generateDiagram(filePath: String): Boolean {
        val plantumlStr = buildPlantUmlString()
        println(
            """
            |================================
            |   $plantumlStr
            |================================
            |   $filePath
        """.trimMargin()
        )

        val file = File(filePath).apply { parentFile.mkdirs() }
        GraphvizUtils.setLocalImageLimit(10000)
        with(
            SourceStringReader(plantumlStr).outputImage(
                FileOutputStream(file),
                FileFormatOption(getFileType(filePath))
            ).description != null
        ) {
            return apply { exportResult(this) }
        }
    }

    private fun getFileType(filePath: String): FileFormat = when (File(filePath).extension) {
        "svg" -> FileFormat.SVG
        "png" -> FileFormat.PNG
        else -> throw IllegalArgumentException("file format error, format: ${File(filePath).extension}")
    }


    interface KeyInfo<T> : DSL<T> {
        fun key_timestamps(vararg timestamps: String)

        fun key_data(vararg data: String)
    }

    object Color {
        const val PINK = "#F0637C"
        const val GREEN = "#6D9D79"
        const val YELLOW = "#CA8422"
        const val TRANSPARENT = "#transparent"
    }
}