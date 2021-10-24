package doxflow.dsl

import doxflow.diagram_8x_flow.generateGenerics
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.ability.BusinessAbilityTable.Row
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.ONE_TO_ONE
import doxflow.models.diagram.Role

class rfp(name: String, context: context, val role: Role, note: String? = null) :
    Evidence<rfp>(name, context, generateGenerics(role), note) {
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

    override fun addBusinessAbility(table: BusinessAbilityTable) {

        table.addRow(
            Row(
                "POST", "/${resource.pluralize()}",
                "发起$name", "${context.element.name}服务", role.element.name
            )
        )
        table.addRow(
            Row(
                "GET", "/${resource.pluralize()}",
                "查看${name}列表", "${context.element.name}服务"
            )
        )
        table.addRow(
            Row(
                "GET", "/${resource.pluralize()}/{rid}",
                "查看$name", "${context.element.name}服务", role.element.name
            )
        )
        table.addRow(
            Row(
                "PUT", "/${resource.pluralize()}/{rid}",
                "更改$name", "${context.element.name}服务", role.element.name
            )
        )
        table.addRow(
            Row(
                "DELETE", "/${resource.pluralize()}/{rid}",
                "取消$name", "${context.element.name}服务", role.element.name
            )
        )
    }
}