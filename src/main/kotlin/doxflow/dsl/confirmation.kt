package doxflow.dsl

import common.Diagram.Color.YELLOW
import common.Element
import common.Element.Type.CLASS
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE

class confirmation(element: Element, private val fulfillment: fulfillment, party: Party?, note: String? = null) :
    Evidence<confirmation>(element, confirmation::class, party, note) {
    init {
        resource = confirmation::class.java.simpleName
        relationship = ONE_TO_ONE
    }

    /**
     * 当前confirmation是否存在另一个confirmation来扮演它
     * */
    private var dependentConfirmation: confirmation? = null


    /**
     * confirmation角色化
     * */
    fun role(): confirmation = apply {
        element.stereoType = "evidence"
        element.backgroundColor = YELLOW
        isRole = true
    }

    /**
     * 当前confirmation需要依赖其他confirmation确认
     * 例如：周进度检查确认依赖周进度检查条目确认，条目中包括"专栏选题","文章提交", "专栏立项","交稿确认"等数据项
     * */
    infix fun dependent_on(confirmation: confirmation) = confirmation.let {
        role()
        this.dependentConfirmation = it
    }

    fun confirmation(name: String, role: Role? = null, confirmation: confirmation.() -> Unit): confirmation =
        confirmation(Element("${name}确认", CLASS), fulfillment, role).apply {
            role()
            confirmation()
        }

    /**
     * 凭证角色化，让当前凭证扮演confirmation(调用有confirmation变为橙色，当前evidence指向confirmation)
     * */
    infix fun play(confirmation: confirmation) {
        element.relate(confirmation.role().element, Relationship.PLAY_TO)
    }

    override fun invoke(function: confirmation.() -> Unit): confirmation = apply { function() }

    override fun getUriPrefix(): String {
        fulfillment.request.let {
            return BusinessAbilityCreator.getUri(it.resource, it.relationship, it.getUriPrefix())
        }
    }

    override fun toString(): String = buildString {
        appendLine(super.toString())
        appendLine(element.generateRelationships())
        dependentConfirmation?.let {
            appendLine(dependentConfirmation.toString())
            appendLine("""${element.name} $relationship ${it.element.name}""")
        }
    }
}