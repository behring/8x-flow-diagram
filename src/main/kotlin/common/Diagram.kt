package common

import common.Diagram.Color.BLACK
import net.sourceforge.plantuml.FileFormat
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.SourceStringReader
import net.sourceforge.plantuml.cucadiagram.dot.GraphvizUtils
import java.io.File
import java.io.FileOutputStream

interface Diagram {
    enum class LayoutDirection {
        Horizontal,
        Vertical
    }

    enum class Format {
        PNG,
        SVG
    }

    companion object {
        const val ASSOCIATE: String = """ -[${BLACK}]-> """
        const val POSITION: String = """ -[hidden]- """
    }

    fun buildPlantUmlString(): String

    fun exportResult(isSuccess: Boolean) = run { }

    fun exportCompleted() = run { }

    infix fun export(filePath: String) {
        generateDiagram(filePath)
    }

    fun export(filePath: String, vararg format: Format) {
        generateDiagram(filePath, *format)
    }

    /**
     * skinparam backgroundColor transparent
     * skinparam defaultFontColor White
     * skinparam arrowFontColor Black
     * skinparam roundCorner 10
     * hide circle equals skinparam style strictuml
     * skinparam roundCorner 10
     **/
    fun getClassStyle(layoutDirection: LayoutDirection = LayoutDirection.Vertical): String {
        return """
        |skinparam class {
        |   BorderColor black
        |   FontColor White
        |   AttributeFontColor White
        |   StereotypeFontColor White
        |}
        |${if (layoutDirection == LayoutDirection.Vertical) "left to right direction" else ""}
        |skinparam defaultTextAlignment center
        |skinparam style strictuml
        |hide empty members
        """.trimIndent()
    }

    fun getRectangleStyle(): String {
        return """
        |skinparam rectangle {
        |   BorderColor black
        |   FontColor White
        |   BackgroundColor White
        |}
        |skinparam defaultTextAlignment center
        """.trimIndent()
    }

    private fun generateDiagram(filePath: String, vararg format: Format) {
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
        if (format.isEmpty()) {
            generateImage(plantumlStr, file.path)
        } else {
            format.forEach {
                generateImage(plantumlStr, file.path, it)
            }
        }
        exportCompleted()
    }

    private fun generateImage(
        plantumlStr: String,
        imagePath: String,
        format: Format? = null
    ): Boolean {
        val fileFormat = (if (format == null) getFileType(imagePath) else covertDiagramFormatToFileFormat(format))
            ?: throw IllegalArgumentException("image format error. only support SVG and PNG.")

        GraphvizUtils.setLocalImageLimit(10000)
        with(
            SourceStringReader(plantumlStr).outputImage(
                FileOutputStream(generateImageFile(imagePath, format)),
                FileFormatOption(fileFormat)
            ).description != null
        ) {
            return this
        }
    }

    private fun generateImageFile(imagePath: String, format: Format?): File =
        if (format != null) File("$imagePath.${format.name.lowercase()}") else File(imagePath)

    private fun covertDiagramFormatToFileFormat(format: Format): FileFormat = when (format) {
        Format.SVG -> FileFormat.SVG
        Format.PNG -> FileFormat.PNG
    }

    private fun getFileType(filePath: String): FileFormat? = when (File(filePath).extension) {
        "svg" -> FileFormat.SVG
        "png" -> FileFormat.PNG
        else -> null
    }

    interface KeyInfo<T> : DSL<T> {
        fun key_timestamps(vararg timestamps: String)

        fun key_data(vararg data: String)
    }

    object Color {
        const val PINK = "#F0637C"
        const val GREEN = "#6D9D79"
        const val YELLOW = "#CA8522"
        const val PURPLE = "#63507C"
        const val BLUE = "#4BA1AC"
        const val WAVE_BLUE = "#043D4E"
        const val GREY = "#EDF1F3"
        const val DARK_GREY = "#Gray"
        const val BLACK = "#000000"
        const val WHITE = "#FFFFFF"
        const val TRANSPARENT = "#transparent"
    }
}