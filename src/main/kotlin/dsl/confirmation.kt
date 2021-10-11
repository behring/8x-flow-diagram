package dsl

import models.Evidences
import models.Role

class confirmation(role: Role) : Evidences<confirmation>(role.name) {

    override fun invoke(function: confirmation.() -> Unit): confirmation {
        return apply { function() }
    }

    fun evidence(name: String, evidence: evidence.() -> Unit): evidence {
        return evidence(name).apply { evidence() }
    }

    fun party(evidence: evidence?) {

    }

    override fun key_timestamps(vararg timestamps: String) {
    }

}