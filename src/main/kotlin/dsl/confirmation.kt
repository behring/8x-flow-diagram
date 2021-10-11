package dsl

import models.Evidence

class confirmation(name: String, note: String? = null) : Evidence<confirmation>(name, note) {
    var evidence: evidence? = null

    fun produce(evidence: evidence) {
        this.evidence = evidence
    }

    override fun invoke(function: confirmation.() -> Unit): confirmation = apply { function() }

    override val type: String
        get() = confirmation::class.java.simpleName

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            evidence?.let {
                appendLine(it.toString())
                appendLine("""${name} -- ${it.name}""")
            }
        }
    }
}