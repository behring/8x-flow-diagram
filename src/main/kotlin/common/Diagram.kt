package common

import net.sourceforge.plantuml.FileFormat
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.SourceStringReader
import net.sourceforge.plantuml.cucadiagram.dot.GraphvizUtils
import java.io.File
import java.io.FileOutputStream

/**
 * 表示UML中的一个任意元素
 * type <size:14><b>name</b></size> color
 * package A #yellow
 * */
data class Element(
    var name: String,
    val type: String,
    var backgroundColor: String? = "#transparent"
) {
//    init {
//        name = """"<size:14><b>$name</b></size>""""
//    }
    val childElements: MutableList<Element> = mutableListOf()
}

interface KeyInfo<T> : DSL<T> {
    fun key_timestamps(vararg timestamps: String)

    fun key_data(vararg data: String)
}

interface Interactions {
    fun generateInteractions(element: Element, elementInteractions: List<Pair<String, String>>): String = buildString {
        elementInteractions.forEach {
            // class element指定关系时，不能使用[]括号
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

    fun addElement(element: Element) {}

    fun StringBuilder.addElements(
        elements: List<Element>,
        container: Element
    ) = apply {
        appendLine("${container.type} ${container.name} {")
        generateElementsStr(container, elements, this)
        appendLine("}")
    }

    fun generateElementsStr(container: Element, elements: List<Element>, elementsStr: StringBuilder) {
        val mutableElement = elements.toMutableList()
        if (mutableElement.isEmpty()) return
        do {
            val element = mutableElement.removeFirst()
            elementsStr.append("${element.type} ${element.name} ${element.backgroundColor ?: container.backgroundColor ?: ""}")
            if (element.childElements.isNotEmpty()) {
                elementsStr.appendLine("{")
                generateElementsStr(element, element.childElements, elementsStr)
                elementsStr.appendLine("}")
            } else {
                elementsStr.appendLine()
            }
        } while (mutableElement.isNotEmpty())
    }

}

open class ChildElement(element: Element, container: ParentContainer) : Interactions {
    init {
        container.addElement(element)
    }
}

object Color {
    const val PINK = "#F0637C"
    const val GREEN = "#6D9D79"
    const val YELLOW = "#CA8422"
}

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
}