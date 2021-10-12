package dsl

import models.Evidence

class evidence(name: String) : Evidence<evidence>(name) {
    private var roles: MutableList<confirmation> = mutableListOf()

    infix fun role(confirmation: confirmation) {
        roles.add(confirmation)
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
                appendLine("""$name ..> ${it.name}""")
            }
        }
    }
}