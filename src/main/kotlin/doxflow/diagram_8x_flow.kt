package doxflow

import common.*
import doxflow.dsl.context
import doxflow.models.*
import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

object diagram_8x_flow : DSL<diagram_8x_flow>, Diagram {
    private var contexts: MutableList<context> = mutableListOf()

    fun context(name: String, context: context.() -> Unit) = with(context(name)) {
        contexts.add(this)
        context()
    }

    fun getAssociateLink(type: AssociationType): String = when (type) {
        AssociationType.ONE_TO_ONE -> ONE_TO_ONE
        AssociationType.ONE_TO_N -> ONE_TO_N
        AssociationType.N_TO_N -> N_TO_N
        AssociationType.NONE -> ASSOCIATE
    }

    fun generateGenerics(role: Role?): String? = role?.let {
        """
            < <<${role.type.name.lowercase()}>> \n ${role.name} >
        """.trimIndent()
    }

    override fun invoke(function: diagram_8x_flow.() -> Unit): diagram_8x_flow = apply { function() }

    override infix fun export(filePath: String) {
        generateDiagram(buildString {
            appendLine("@startuml")
//            appendLine("skinparam backgroundColor transparent")
//            appendLine("skinparam defaultFontColor White")
//            appendLine("skinparam arrowFontColor Black")
            appendLine("skinparam classFontColor White")
            appendLine("skinparam classAttributeFontColor White")
            appendLine("skinparam roundCorner 10")
            appendLine("hide circle")
            contexts.forEach { context ->
                appendLine(context.toString())
            }
            appendLine("@enduml")
        }, filePath).apply { if (this) contexts.clear()}

    }

    private fun generateDiagram(plantUmlStr: String, filePath: String): Boolean {
        println(plantUmlStr)
        println(filePath)
        return SourceStringReader(plantUmlStr).outputImage(FileOutputStream(File(filePath))).description != null
    }
}



