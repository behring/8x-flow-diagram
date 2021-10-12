package dsl

import models.Evidence

class confirmation(name: String, note: String? = null) : Evidence<confirmation>(name, note) {
    var evidence: evidence? = null
    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        this.evidence = evidence(name)
        return this.evidence!!.apply { evidence() }
    }

    fun generateClassesInContext(sb: StringBuilder) = evidence?.let {
        sb.appendLine("class ${it.name}")
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