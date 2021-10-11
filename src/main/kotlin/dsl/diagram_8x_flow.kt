package dsl

import models.Role
import net.sourceforge.plantuml.SourceStringReader
import java.io.File
import java.io.FileOutputStream

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

    fun role_party(name: String): Role = Role(name, Role.Type.PARTY)

    fun createDiagram(filePath: String) {
        generateDiagram(buildString {
            appendLine("@startuml")
            appendLine("skinparam defaultFontColor White")
            appendLine("skinparam objectBackgroundColor HotPink")
            contexts.forEach { context ->
                context.contracts.forEach { contract ->
                    appendLine(contract.toString())
                }
            }
            appendLine("@enduml")
        }, filePath)

    }

    private fun generateDiagram(plantUmlStr: String, filePath: String): Boolean =
        SourceStringReader(plantUmlStr).outputImage(FileOutputStream(File(filePath))).description != null
}



