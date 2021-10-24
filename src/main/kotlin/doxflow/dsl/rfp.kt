package doxflow.dsl

import doxflow.diagram_8x_flow.generateGenerics
import doxflow.models.Evidence
import doxflow.models.ONE_TO_ONE
import doxflow.models.Role

class rfp(name: String, context: context, generics: String?, note: String? = null) :
    Evidence<rfp>(name, context, generics, note) {
    private lateinit var proposal: proposal

    fun proposal(name: String, role: Role, proposal: proposal.() -> Unit) {
        this.proposal = proposal(name, context, generateGenerics(role)).apply {
            context.proposals.add(this)
            proposal()
        }
    }

    override fun invoke(function: rfp.() -> Unit): rfp {
        return apply { function() }
    }

    override val type: String
        get() = rfp::class.java.simpleName

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine("$name$ONE_TO_ONE${proposal.name}")
        }
    }

    override fun toApiString(): String = buildString {
        // TODO: 2021/10/24  
    }
}