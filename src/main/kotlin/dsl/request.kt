package dsl

import models.Evidences
import models.Role
import java.util.*

class request(name: String, val role: Role, note: String? = null) : Evidences<request>(name, note) {

    override fun invoke(function: request.() -> Unit): request = apply { function() }

    override val type: String
        get() = request::class.java.simpleName
}