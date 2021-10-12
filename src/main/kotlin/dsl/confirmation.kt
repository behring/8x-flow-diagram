package dsl

import models.Evidence

class confirmation(name: String, context: context, note: String? = null) : Evidence<confirmation>(name, context, note) {
    var evidence: evidence? = null
    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        this.evidence = evidence(name, context)
        return this.evidence!!.apply { evidence() }
    }

    override fun invoke(function: confirmation.() -> Unit): confirmation = apply { function() }

    override val type: String
        get() = confirmation::class.java.simpleName

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            evidence?.let {
                appendLine(evidence.toString())
                appendLine("""${it.name} $ASSOCIATE $name""")
            }
        }
    }
}