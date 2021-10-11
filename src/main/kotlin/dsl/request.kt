package dsl

import models.Evidences
import models.Role

class request(role: Role) : Evidences<request>(role.name) {

    override fun invoke(function: request.() -> Unit): request {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {
    }

    override val type: String
        get() = request::class.java.simpleName
}