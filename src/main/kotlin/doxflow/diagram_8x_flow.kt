package doxflow

import architecture.models.Element
import common.*
import doxflow.dsl.context
import doxflow.models.*

interface BusinessAbility<T> : DSL<T> {
    var resource: String?

    fun String.pluralize(): String {
        val vowel = arrayOf('a', 'e', 'i', 'o', 'u')
        val specificPlurals = mapOf("person" to "people")
        if (this.isBlank()) return this
        return when {
            specificPlurals.containsKey(this) -> specificPlurals[this].toString()
            endsWith('s') || endsWith('x') || endsWith("ch") || endsWith("sh") -> this + "es"
            !vowel.contains(this[length - 2]) && last() == 'y' -> this.substring(0, length - 1) + "ies"
            else -> this + 's'
        }
    }
}

object diagram_8x_flow : DSL<diagram_8x_flow>, Diagram {
    private var contexts: MutableList<context> = mutableListOf()

    fun context(name: String, context: context.() -> Unit) = with(context(Element(name, "package"))) {
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
            < <<${role.type.name.lowercase()}>> \n ${role.element.name} >
        """.trimIndent()
    }

    override fun invoke(function: diagram_8x_flow.() -> Unit): diagram_8x_flow = apply { function() }

    /**
     * skinparam backgroundColor transparent
     * skinparam defaultFontColor White
     * skinparam arrowFontColor Black
     **/
    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam classFontColor White
        |skinparam classAttributeFontColor White
        |skinparam roundCorner 10
        |hide circle
        ${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    override fun exportResult(isSuccess: Boolean) {
        if (isSuccess) contexts.clear()
    }

    private fun buildPlantUmlContent(): String = buildString {
        contexts.forEach { context ->
            appendLine(context.toString())
        }
    }
}



