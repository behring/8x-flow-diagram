package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Role

class request(element: Element, private val fulfillment: fulfillment, role: Role?, note: String? = null) :
    Evidence<request>(element, request::class, role, note) {
    init {
        resource = fulfillment.resource
        relationship = fulfillment.relationship
    }

    override fun invoke(function: request.() -> Unit): request = apply { function() }

    override fun getUriPrefix(): String {
        fulfillment.contract.let {
            return BusinessAbilityCreator.getUri(it.resource, it.relationship, it.getUriPrefix())
        }
    }
}