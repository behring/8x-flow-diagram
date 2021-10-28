package doxflow.dsl

import common.Element
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Role

class request(element: Element, context: context, role: Role?, note: String? = null) :
    Evidence<request>(element, context, role, note) {

    lateinit var contract: contract

    override fun invoke(function: request.() -> Unit): request = apply { function() }

    override fun getUriPrefix(): String {
        return contract.getUri()
    }

    override val type: String
        get() = request::class.java.simpleName
}