package dsl

import models.Evidences
import models.Role

class request(name:String, role: Role) : Evidences<request>(name) {

    override fun invoke(function: request.() -> Unit): request {
        return apply { function() }
    }

    override val type: String
        get() = request::class.java.simpleName
}