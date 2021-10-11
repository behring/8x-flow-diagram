package dsl

import models.Participant

class context(val name: String) : Flow<context> {
    var contracts: MutableList<contract> = mutableListOf()

    override fun invoke(function: context.() -> Unit): context {
        return apply { function() }
    }

    fun participant_party(name: String): Participant = Participant(name, Participant.Type.PARTY)

    fun contract(name: String, contract: contract.() -> Unit) = with(contract(name)) {
        contracts.add(this)
        contract()
    }
}