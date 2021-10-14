package common

import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

interface DSL<T> {
    operator fun invoke(function: T.() -> Unit): T
}

interface TopContainer {
    fun addElement(element: String)

    fun StringBuilder.addElements(
        elements: List<String>,
        topContainerName: String,
        topContainerType: String
    ) = apply {
        appendLine("$topContainerType <color:black>$topContainerName</color> {")
        elements.forEach { appendLine(it) }
        appendLine("}")
    }
}

open class ChildElement(val name: String, topContainer: TopContainer) {
    init {
        topContainer.addElement(name)
    }
}


interface Diagram {
    fun buildPlantUmlString(): String

    fun exportResult(isSuccess: Boolean) = run { }

    infix fun export(filePath: String) {
        generateDiagram(filePath)
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
        with(SourceStringReader(plantumlStr).outputImage(FileOutputStream(file)).description != null) {
            return apply { exportResult(this) }
        }
    }
}