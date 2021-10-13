package common

import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

interface DSL<T> {
    operator fun invoke(function: T.() -> Unit): T
}

interface Diagram {
    fun buildPlantUmlString(): String

    fun exportResult(isSuccess: Boolean) = run { }

    infix fun export(filePath: String) {
        generateDiagram(filePath)
    }

    private fun generateDiagram(filePath: String): Boolean {
        val plantumlStr = buildPlantUmlString()
        println(plantumlStr)
        println(filePath)
        with(SourceStringReader(plantumlStr).outputImage(FileOutputStream(File(filePath))).description != null) {
            return apply { exportResult(this) }
        }

    }
}