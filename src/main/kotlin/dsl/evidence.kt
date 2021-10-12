package dsl

import models.Evidence

class evidence(name: String, context: context) : Evidence<evidence>(name, context) {
    private var roles: MutableList<confirmation> = mutableListOf()
    var detail: detail? = null

    infix fun role(confirmation: confirmation) {
        roles.add(confirmation.role())
    }

    fun detail(name: String, detail: detail.() -> Unit): detail {
        this.detail = detail(name, context)
        return this.detail!!.apply { detail() }
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
            detail?.let {
                appendLine(it.toString())
                appendLine("""$name $ONE_TO_N ${it.name}""")
            }
        }
    }
}