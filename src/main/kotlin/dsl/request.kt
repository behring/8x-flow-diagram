package dsl

import models.Evidences
import models.Role

class request(role: Role) : Evidences(role.name, Type.REQUEST), Flow<request> {

    override fun invoke(function: request.() -> Unit): request {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {
    }
}