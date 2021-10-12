package dsl

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

    fun createDiagram(filePath: String) {
        generateDiagram(buildString {
            appendLine("@startuml")
//            appendLine("skinparam backgroundColor transparent")
//            appendLine("skinparam defaultFontColor White")
//            appendLine("skinparam arrowFontColor Black")
            appendLine("skinparam classFontColor White")
            appendLine("skinparam classAttributeFontColor White")
            appendLine("skinparam classBackgroundColor HotPink")
            appendLine("skinparam roundCorner 10")
            appendLine("hide circle")
            contexts.forEach { context ->
                appendLine(context.toString())
                context.participants.forEach {
                    appendLine(it.toString())
                }

                context.contracts.forEach { contract ->
                    appendLine(contract.toString())
                    contract.roles.forEach { role ->
                        appendLine(role.toString())
                        appendLine("""${contract.name} "1"--"1" ${role.name}""")
                        role.participant?.let {
                            appendLine("""${it.name} ..> ${role.name}""")
                        }
                    }

                    contract.fulfillments.forEach {fulfillment ->
                        appendLine("""${contract.name} "1"--"n" ${fulfillment.request.name}""")
                        appendLine(fulfillment.request.toString())
                        appendLine("""${fulfillment.request.name} "1"--"1" ${fulfillment.confirmation.name}""")
                        appendLine(fulfillment.confirmation.toString())
                    }
                }
            }
            appendLine("@enduml")
        }, filePath)

    }

    private fun generateDiagram(plantUmlStr: String, filePath: String): Boolean {
        println(plantUmlStr)
        return SourceStringReader(plantUmlStr).outputImage(FileOutputStream(File(filePath))).description != null
    }

}



