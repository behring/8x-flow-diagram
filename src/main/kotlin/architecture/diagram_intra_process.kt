package architecture

import architecture.dsl.intra_process.layer
import architecture.dsl.intra_process.process
import common.DSL
import common.Diagram
import common.Element

object diagram_intra_process : DSL<diagram_intra_process>, Diagram {
    private var layers: MutableList<layer> = mutableListOf()
    private val processes: MutableList<process> = mutableListOf()

    fun layer(name: String, color: String? = null, function: layer.() -> Unit): layer =
        layer(Element(name, "rectangle", color)).apply {
            layers.add(this)
            function()
        }

    fun process(name: String, color: String? = "#Gray", function: (process.() -> Unit)? = null): process =
        process(Element(name, "cloud", color)).apply {
            processes.add(this)
            function?.let { it() }
        }

    override fun invoke(function: diagram_intra_process.() -> Unit): diagram_intra_process = apply { function() }

    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam componentStyle rectangle
        |skinparam rectangleFontColor black
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
        layers.forEach {
            appendLine(it.toString())
        }
        processes.forEach {
            appendLine(it.toString())
        }
        /**
         * 注意：此方法不能再layers.forEach中继续对component进行forEach调用，
         * 必须在所有的layer构建完成后再构建component，否则生成图中，component会脱离layer
         * */
        layers.flatMap { it.components }.forEach {
            appendLine(it.toString())
        }
    }
}