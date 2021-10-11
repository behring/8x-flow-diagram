package dsl

import models.Evidence

class request(name: String, note: String? = null) : Evidence<request>(name, note) {

    override fun invoke(function: request.() -> Unit): request = apply { function() }

    override val type: String
        get() = request::class.java.simpleName
}