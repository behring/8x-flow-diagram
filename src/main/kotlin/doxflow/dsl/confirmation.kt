package doxflow.dsl

import common.Element
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.NONE

class confirmation(element: Element, private val fulfillment: fulfillment, party: Party?, note: String? = null) :
    Evidence<confirmation>(element, confirmation::class, party, note) {
    /**
     * 当前confirmation是否存在一个evidence去扮演它
     * */
    private var evidence: evidence? = null

    /**
     * 当前confirmation是否存在另一个confirmation来扮演它
     * */
    private var dependentConfirmation: confirmation? = null
    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        this.evidence = evidence(Element(name, "class"))
        return this.evidence!!.apply { evidence() }
    }

    /**
     * confirmation角色化
     * */
    fun role(): confirmation = apply { isRole = true }

    /**
     * 当前confirmation需要依赖其他confirmation确认
     * 例如：周进度检查确认依赖周进度检查条目确认，条目中包括"专栏选题","文章提交", "专栏立项","交稿确认"等数据项
     * */
    infix fun dependent_on(confirmation: confirmation) = confirmation.let {
        role()
        this.dependentConfirmation = it
    }

    fun confirmation(name: String, role: Role? = null, confirmation: confirmation.() -> Unit): confirmation =
        confirmation(Element("${name}确认", "class"), fulfillment, role).apply {
            role()
            confirmation()
        }


    override fun invoke(function: confirmation.() -> Unit): confirmation = apply { function() }

    override fun toString(): String = buildString {
        appendLine(super.toString())
        evidence?.let {
            appendLine(evidence.toString())
            appendLine("""${it.element.displayName} $NONE ${element.displayName}""")
        }
        dependentConfirmation?.let {
            appendLine(dependentConfirmation.toString())
            appendLine("""${element.displayName} $relationship ${it.element.displayName}""")
        }
    }
}