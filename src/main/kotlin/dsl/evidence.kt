package dsl

import models.Evidence

class evidence(name: String) : Evidence<evidence>(name) {
    private var roles: MutableList<confirmation> = mutableListOf()
    var detail: detail? = null

    infix fun role(confirmation: confirmation) {
        roles.add(confirmation)
    }

    fun detail(name: String, detail: detail.() -> Unit): detail {
        this.detail = detail(name)
        return this.detail!!.apply { detail() }
    }

    fun generateClassesInContext(sb: StringBuilder) = detail?.let {
        sb.appendLine("class ${it.name}")
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