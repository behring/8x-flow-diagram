package doxflow.dsl

import doxflow.diagram_8x_flow.getAssociateLink
import doxflow.models.diagram.AssociationType
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.PLAY_TO

class evidence(name: String, context: context) : Evidence<evidence>(name, context) {
    private var roles: MutableList<confirmation> = mutableListOf()
    var detailAssociation: Pair<detail, AssociationType>? = null

    infix fun role(confirmation: confirmation) {
        roles.add(confirmation.role())
    }

    fun detail(
        name: String,
        associationType: AssociationType = AssociationType.NONE,
        detail: detail.() -> Unit
    ): detail {
        return detail(name, context).apply {
            detailAssociation = Pair(this, associationType)
            detail()
        }
    }

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override val type: String
        get() = evidence::class.java.simpleName

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            roles.forEach {
                appendLine("""$name $PLAY_TO ${it.name}""")
            }
            detailAssociation?.let {
                appendLine(it.first.toString())
                appendLine("""$name ${getAssociateLink(it.second)} ${it.first.name}""")
            }
        }
    }
}