package dsl

import models.Evidences
import models.Role
import java.util.*

class confirmation(name: String, val role: Role, note: String? = null) : Evidences<confirmation>(name, note) {

    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        return evidence(name).apply { evidence() }
    }

    fun party(evidence: evidence?) {

    }

    override fun invoke(function: confirmation.() -> Unit): confirmation = apply { function() }

    override val type: String
        get() = confirmation::class.java.simpleName
}