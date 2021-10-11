package dsl

import models.Evidences
import models.Role
import java.util.*

class request(name: String, note: String? = null) : Evidences<request>(name, note) {

    override fun invoke(function: request.() -> Unit): request = apply { function() }

    override val type: String
        get() = request::class.java.simpleName
}