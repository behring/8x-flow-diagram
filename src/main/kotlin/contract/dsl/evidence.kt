package contract.dsl

import common.KeyInfo

class contract(name: String) : evidence(name)

class proposal(name: String) : evidence(name)

class rfp(name: String) : evidence(name)

open class evidence(val name: String) : KeyInfo<evidence> {
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null
    private var preorderEvidence: evidence? = null
    private var postorderEvidences: MutableList<evidence> = mutableListOf()
    private var contractName: String? = null

    infix fun preorder(evidence: evidence) = evidence.let { preorderEvidence = it }

    infix fun postorder(evidence: evidence) = postorderEvidences.add(evidence)

    infix fun belong_to(contractName: String) = contractName.let { this.contractName = it }

    override fun invoke(function: evidence.() -> Unit): evidence = apply { function() }

    override fun key_timestamps(vararg timestamps: String) = timestamps.let { this.timestamps = it }

    override fun key_data(vararg data: String) = data.let { this.data = it }

    override fun toString(): String = buildString {
        appendLine(
            """
            class $name<<${this@evidence.javaClass.simpleName}>> #HotPink {
                ${if (timestamps != null) timestamps.contentToString() else ""}
                ${if (timestamps != null && data != null) ".." else ""}
                ${if (data != null) data.contentToString() else ""}
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