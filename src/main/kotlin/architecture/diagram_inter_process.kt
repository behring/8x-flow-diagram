package architecture

import architecture.dsl.layer
import common.DSL
import common.Diagram

object diagram_inter_process : DSL<diagram_inter_process>, Diagram {

    fun layer(name: String, function: layer.() -> Unit) = with(layer(name)) { function() }

    override fun invoke(function: diagram_inter_process.() -> Unit): diagram_inter_process = apply { function() }

    override fun buildPlantUmlString(): String {
        return ""
    }
}