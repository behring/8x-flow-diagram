package doxflow.dsl

import doxflow.models.diagram.ASSOCIATE
import doxflow.models.diagram.Evidence

class confirmation(name: String, context: context, generics: String?, note: String? = null) :
    Evidence<confirmation>(name, context, generics, note) {
    var evidence: evidence? = null
    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        this.evidence = evidence(name, context)
        return this.evidence!!.apply { evidence() }
    }

    fun role(): confirmation = apply { isRole = true }

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