package doxflow

import common.Element
import common.*
import doxflow.dsl.context
import doxflow.models.diagram.*

object diagram_8x_flow : DSL<diagram_8x_flow>, Diagram, Doc {
    var currentLegend = LegendType.TacticalLegend
    enum class LegendType {
        StrategicLegend,
        TacticalLegend
    }

    private var contexts: MutableList<context> = mutableListOf()

    fun context(name: String, context: context.() -> Unit) = with(context(Element(name, "rectangle"))) {
        contexts.add(this)
        context()
    }

    fun export_diagram_and_doc(diagram: String, doc: String) {
        export(diagram)
        export_doc(doc)
    }

    override fun invoke(function: diagram_8x_flow.() -> Unit): diagram_8x_flow = apply {
        function()
    }

    override fun buildDocContent(): String = buildString {
        appendLine("# 服务与业务能力")
        appendLine(buildApiDocContent())
    }

    override fun buildPlantUmlString(): String = """
        |@startuml
        ${getClassStyle()}
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

    override fun exportDocCompleted() {
        contexts.clear()
    }

    private fun buildApiDocContent(): String = buildString {
        contexts.forEach { context ->
            appendLine(context.toApiString())
        }
    }
}



