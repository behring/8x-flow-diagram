package dsl

import models.Evidences
import models.Role

class confirmation(name: String, role: Role) : Evidences<confirmation>(name) {

    override fun invoke(function: confirmation.() -> Unit): confirmation {
        return apply { function() }
    }

    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        return evidence(name).apply { evidence() }
    }

    fun party(evidence: evidence?) {

    }

    override val type: String
        get() = confirmation::class.java.simpleName
}