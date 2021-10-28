package contract.analysis.dsl

import common.Diagram
import common.Diagram.Color.PINK
import contract.analysis.diagram_contract_analysis.contractToEvidences

class contract(name: String) : evidence(name) {
    init {
        belong_to(name)
    }
}

class proposal(name: String) : evidence(name)

class rfp(name: String) : evidence(name)

open class evidence(val name: String) : Diagram.KeyInfo<evidence> {
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null
    private var preorderEvidence: evidence? = null
    private var postorderEvidences: MutableList<evidence> = mutableListOf()

    //当一个凭证属于2个协议的时候，表明该凭证存在凭证角色化
    private var contracts: MutableList<String> = mutableListOf()

    infix fun preorder(evidence: evidence) = evidence.let { preorderEvidence = it }

    infix fun postorder(evidence: evidence) = postorderEvidences.add(evidence)

    infix fun belong_to(contract: String) {
        contracts.add(contract)
        val contractName = contracts.joinToString("+")
        if (contracts.size > 1) {
            val copy = HashMap(contractToEvidences)
            contractToEvidences.clear()
            contractToEvidences.computeIfAbsent(contractName) { mutableListOf() }
            contractToEvidences[contractName]?.add(this)
            contractToEvidences.putAll(copy)
        } else {
            contractToEvidences.computeIfAbsent(contractName) { mutableListOf() }
            contractToEvidences[contractName]?.add(this)
        }
    }

    override fun invoke(function: evidence.() -> Unit): evidence = apply { function() }

    override fun key_timestamps(vararg timestamps: String) = timestamps.let { this.timestamps = it }

    override fun key_data(vararg data: String) = data.let { this.data = it }

    override fun toString(): String = buildString {
        appendLine(
            """
            class $name<<${this@evidence.javaClass.simpleName}>> $PINK {
                ${timestamps?.joinToString() ?: ""}
                ${if (timestamps != null && data != null) ".." else ""}
                ${data?.joinToString() ?: ""}
            }
        """.trimIndent()
        )
        preorderEvidence?.let {
            appendLine("${name}-->${it.name}:前序凭证")
        }
        postorderEvidences.forEach {
            appendLine("${name}-->${it.name}:后序凭证")
        }
    }
}