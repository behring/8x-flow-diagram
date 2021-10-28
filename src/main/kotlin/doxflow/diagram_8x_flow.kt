package doxflow

import common.Element
import common.*
import doxflow.dsl.context
import doxflow.models.diagram.*

object diagram_8x_flow : DSL<diagram_8x_flow>, Diagram, Doc {
    private var contexts: MutableList<context> = mutableListOf()

    fun context(name: String, context: context.() -> Unit) = with(context(Element(name, "rectangle"))) {
        contexts.add(this)
        context()
    }

    fun getRelationshipLine(type: RelationShipType): String = when (type) {
        RelationShipType.ONE_TO_ONE -> ONE_TO_ONE
        RelationShipType.ONE_TO_N -> ONE_TO_N
        RelationShipType.N_TO_N -> N_TO_N
        RelationShipType.NONE -> RELATIONSHIP
    }

    fun generateRole(role: Role?): String? = role?.let {
        """
            <size:16><back:${role.element.color}>${role.element.name}</back></size>
            |..
            |
        """.trimIndent()
    }

    fun export_diagram_and_doc(diagram: String, doc: String) {
        export(diagram)
        export_doc(doc)
    }

    override fun invoke(function: diagram_8x_flow.() -> Unit): diagram_8x_flow = apply { function() }

    override fun buildDocContent(): String = buildString {
        appendLine("# 服务与业务能力")
        appendLine(buildApiDocContent())
    }

    /**
     * skinparam backgroundColor transparent
     * skinparam defaultFontColor White
     * skinparam arrowFontColor Black
     * skinparam roundCorner 10
     * hide circle equals skinparam style strictuml
     **/
    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam class {
        |   BorderColor black
        |   FontColor White
        |   AttributeFontColor White
        |   StereotypeFontColor White
        |}
        |skinparam defaultTextAlignment center
        |skinparam style strictuml
        |hide empty members
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

    private fun buildApiDocContent(): String = buildString {
        contexts.forEach { context ->
            appendLine(context.toApiString())
        }
    }
}



