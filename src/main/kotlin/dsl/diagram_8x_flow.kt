package dsl

import models.Role
import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

const val ONE_TO_N = """ "1" -- "N" """
const val ONE_TO_ONE = """ "1" -- "1" """
const val ASSOCIATE = """ -- """
const val PLAY_TO = """ ..> """

interface Flow<T> {
    operator fun invoke(function: T.() -> Unit): T
}

object diagram_8x_flow : Flow<diagram_8x_flow> {
    private var contexts: MutableList<context> = mutableListOf()

    override fun invoke(function: diagram_8x_flow.() -> Unit): diagram_8x_flow {
        return apply { function() }
    }

    fun context(name: String, context: context.() -> Unit) = with(context(name)) {
        contexts.add(this)
        context()
    }

    fun generateGenerics(role: Role?): String? = role?.let {
        """
            < <<${role.type.name.lowercase()}>> \n ${role.name} >
        """.trimIndent()
    }

    fun createDiagram(filePath: String) {
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
        }, filePath)

    }

    private fun generateDiagram(plantUmlStr: String, filePath: String): Boolean {
        println(plantUmlStr)
        return SourceStringReader(plantUmlStr).outputImage(FileOutputStream(File(filePath))).description != null
    }
}



