package architecture

import architecture.dsl.intra_process.layer
import architecture.dsl.intra_process.process
import common.DSL
import common.Diagram
import common.Diagram.Color.GREY
import common.Element
import common.Element.Type.CLOUD
import common.Element.Type.RECTANGLE

class diagram_intra_process(val name: String = "", function: diagram_intra_process.() -> Unit) : DSL<diagram_intra_process>, Diagram {
    private var layers: MutableList<layer> = mutableListOf()
    private val processes: MutableList<process> = mutableListOf()
    init {
        function()
    }

    fun layer(name: String, color: String? = null, function: layer.() -> Unit): layer =
        layer(Element(name, RECTANGLE, color)).apply {
            layers.add(this)
            function()
        }

    fun process(name: String, color: String? = GREY, function: (process.() -> Unit)? = null): process =
        process(Element(name, CLOUD, color)).apply {
            processes.add(this)
            function?.let { it() }
        }

    override fun invoke(function: diagram_intra_process.() -> Unit): diagram_intra_process = apply { function() }

    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam ${RECTANGLE}FontColor white
        ${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    override fun exportResult(isSuccess: Boolean) {
        if (isSuccess) {
            layers.clear()
            processes.clear()
        }
    }

    private fun buildPlantUmlContent(): String = buildString {
        appendLine("$RECTANGLE <size:20>$name {")
        layers.forEach {
            appendLine(it.toString())
        }
        appendLine("}")
        processes.forEach {
            appendLine(it.toString())
        }
        // 最后生成关联关系
        processes.forEach {
            appendLine(it.element.generateRelationships())
        }
        layers.forEach {
            appendLine(it.generateComponentRelationships())
        }
    }
}