package doxflow.dsl

import doxflow.diagram_8x_flow.getRelationshipLine
import doxflow.models.diagram.RelationShipType
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.PLAY_TO

class evidence(name: String, context: context) : Evidence<evidence>(name, context) {
    private var roles: MutableList<confirmation> = mutableListOf()
    var detailAssociation: Pair<detail, RelationShipType>? = null

    /**
     * 凭证角色化，让当前凭证扮演confirmation(调用有confirmation变为橙色，当前evidence指向confirmation)
     * */
    infix fun role(confirmation: confirmation) {
        roles.add(confirmation.role())
    }

    fun detail(
        name: String,
        relationShipType: RelationShipType = RelationShipType.NONE,
        detail: detail.() -> Unit
    ): detail {
        return detail(name, context).apply {
            detailAssociation = Pair(this, relationShipType)
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
                appendLine("""$name ${getRelationshipLine(it.second)} ${it.first.name}""")
            }
        }
    }
}