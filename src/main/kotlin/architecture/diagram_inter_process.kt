package architecture

import common.DSL
import common.Diagram

object diagram_inter_process : DSL<diagram_inter_process>, Diagram {
    override fun invoke(function: diagram_inter_process.() -> Unit): diagram_inter_process = apply { function() }
    override infix fun export(filePath: String) {

    }
}