package doxflow.dsl

import common.Element
import common.Element.Type.CLASS
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Relationship.Companion.DEFAULT
import doxflow.models.diagram.Relationship.Companion.PLAY_TO

class evidence(element: Element) : Evidence<evidence>(element, evidence::class) {
    var detail: detail? = null

    /**
     * 凭证角色化，让当前凭证扮演confirmation(调用有confirmation变为橙色，当前evidence指向confirmation)
     * */
    infix fun play(confirmation: confirmation) {
        element.relate(confirmation.role().element, PLAY_TO)
    }

    fun play(confirmation: confirmation, hideParty: Boolean) {
        element.relate(confirmation.role(hideParty).element, PLAY_TO)
    }

    fun detail(
        name: String,
        relationship: String = DEFAULT,
        detail: detail.() -> Unit
    ): detail {
        return detail(Element(name, CLASS)).apply {
            this@evidence.detail = this
            detail()
            element.relate(this.element, relationship)
        }
    }

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            detail?.let { appendLine(detail.toString()) }
        }
    }
}