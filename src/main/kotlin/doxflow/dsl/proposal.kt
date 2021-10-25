package doxflow.dsl

import doxflow.models.diagram.*

class proposal(name: String, context: context, role: Role?, note: String? = null) :
    Evidence<proposal>(name, context, role, note) {
    private lateinit var contract: contract
    init {
        resource = type
    }
    // 当前proposal是否有对应的rfp
    var rfp: rfp? = null

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) {
        this.contract = contract(name, context, *roles).apply {
            context.contracts.add(this)
            contract()
        }
    }

    override val type: String
        get() = proposal::class.java.simpleName

    override fun invoke(function: proposal.() -> Unit): proposal = apply { function() }

    override fun getUriPrefix(): String {
        rfp?.let {
            return it.getUri()
        }
        return super.getUriPrefix()
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine("$name $ONE_TO_ONE ${contract.name}")
        }
    }
}