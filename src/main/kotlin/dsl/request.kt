package dsl

import models.Evidence

class request(name: String, context: context, generics: String?, note: String? = null) :
    Evidence<request>(name, context, generics, note) {

    override fun invoke(function: request.() -> Unit): request = apply { function() }

    override val type: String
        get() = request::class.java.simpleName
}