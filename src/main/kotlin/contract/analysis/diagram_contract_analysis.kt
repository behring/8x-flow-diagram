package contract.analysis

import common.*
import contract.analysis.dsl.contract
import contract.analysis.dsl.evidence
import contract.analysis.dsl.proposal
import contract.analysis.dsl.rfp

object diagram_contract_analysis : DSL<diagram_contract_analysis>, Diagram {
    var contractToEvidences: MutableMap<String, MutableList<evidence>> = linkedMapOf()
    private var evidences: MutableList<evidence> = mutableListOf()

    fun evidence(name: String, function: evidence.() -> Unit): evidence = dsl(name, function)

    fun rfp(name: String, function: rfp.() -> Unit): rfp = dsl(name, function)

    fun proposal(name: String, function: proposal.() -> Unit): proposal = dsl(name, function)

    fun contract(name: String, function: contract.() -> Unit): contract = dsl(name, function)

    private inline fun <reified T> dsl(name: String, function: T.() -> Unit) =
        T::class.java.getDeclaredConstructor(String::class.java).newInstance(name).apply {
            if (this is evidence) {
                evidences.add(this)
                function()
            }
        }

    override fun buildPlantUmlString(): String = """
        |@startuml
        ${getClassStyle()}
        |${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    private fun buildPlantUmlContent(): String = buildString {
        createContracts(this)
        evidences.forEach { appendLine(it.toString()) }
    }

    private fun createContracts(sb: StringBuilder) {
        contractToEvidences.forEach { (contract, evidences) ->
            sb.appendLine("package $contract {")
            evidences.forEach { evidence ->
                sb.appendLine("class ${evidence.name}")
            }
            sb.appendLine("}")
        }
    }

    override fun invoke(function: diagram_contract_analysis.() -> Unit): diagram_contract_analysis =
        apply { function() }
}



