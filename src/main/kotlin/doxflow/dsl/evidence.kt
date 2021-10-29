package doxflow.dsl

import common.Element
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Relationship.Companion.NONE
import doxflow.models.diagram.Relationship.Companion.PLAY_TO

class evidence(element: Element, context: context) : Evidence<evidence>(element, context, evidence::class) {
    private var roles: MutableList<confirmation> = mutableListOf()
    var detailRelationship: Pair<detail, String>? = null

    /**
     * 凭证角色化，让当前凭证扮演confirmation(调用有confirmation变为橙色，当前evidence指向confirmation)
     * */
    infix fun role(confirmation: confirmation) {
        roles.add(confirmation.role())
    }

    fun detail(
        name: String,
        relationship: String = NONE,
        detail: detail.() -> Unit
    ): detail {
        return detail(Element(name, "class"), context).apply {
            detailRelationship = Pair(this, relationship)
            detail()
        }
    }

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            roles.forEach {
                appendLine("""${element.displayName} $PLAY_TO ${it.element.displayName}""")
            }
            detailRelationship?.let {
                appendLine(it.first.toString())
                appendLine("""${element.displayName} ${it.second} ${it.first.element.displayName}""")
            }
        }
    }
}