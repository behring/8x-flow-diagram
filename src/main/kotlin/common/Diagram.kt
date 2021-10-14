package common

import architecture.models.Element
import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

interface DSL<T> {
    operator fun invoke(function: T.() -> Unit): T
    fun generateInteractions(element: Element, elementInteractions: List<Pair<String, String>>): String = buildString {
        elementInteractions.forEach {
            append("[${element.name}]-->[${it.first}]")
            appendLine(with(it.second) {
                return@with if (!isNullOrBlank()) ":${it.second}" else ""
            })
        }
    }
}

interface ParentContainer {
    val element: Element
    val backgroundColor: String?
        get() = null

    fun addElement(element: Element)

    fun StringBuilder.addElements(
        elements: List<String>,
        container: Element
    ) = appendLine(
        """
        |${container.type} <color:black>${container.name}</color> {
        |   ${generateElementsStr(elements)}
        |}""".trimMargin()
    )

    private fun generateElementsStr(elements: List<String>) = buildString {
        elements.forEach { appendLine(it) }
    }
}

open class ChildElement(element: Element, container: ParentContainer) {
    constructor(name: String, type: String, container: ParentContainer) : this(Element(name, type), container)

    init {
        container.addElement(element)
    }
}

object Color {

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