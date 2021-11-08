package doxflow

import common.Element
import common.*
import doxflow.dsl.context

data class diagram_8x_flow(val function: diagram_8x_flow.() -> Unit) : Diagram, Doc {

    private var contexts: MutableList<context> = mutableListOf()

    init {
        this.function()
    }

    companion object {
        var currentLegend = LegendType.TacticalLegend
    }

    enum class LegendType {
        StrategicLegend,
        TacticalLegend
    }

    fun context(name: String, context: (context.() -> Unit)? = null): context =
        context(Element(name, "rectangle")).apply {
            contexts.add(this)
            context?.let { it() }
        }

    fun export_diagram_and_doc(diagram: String, doc: String) {
        export(diagram)
        export_doc(doc)
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
        contexts.forEach {
            appendLine(it.toString())
        }
    }

    override fun exportDocCompleted() {
        contexts.clear()
    }

    private fun buildApiDocContent(): String = buildString {
        contexts.forEach {
            appendLine(it.toApiString())
        }
    }
}



