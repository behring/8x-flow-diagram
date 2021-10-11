package dsl

import models.Role

interface Flow<T> {
    operator fun invoke(function: T.() -> Unit): T
}

object diagram_8x_flow : Flow<diagram_8x_flow> {
    lateinit var plantUmlDiagram: StringBuilder

    override fun invoke(function: diagram_8x_flow.() -> Unit): diagram_8x_flow {
        return apply { function() }
    }

    fun context(name: String, context: context.() -> Unit) = context(name).context()

    fun role_party(name: String): Role = Role(name, Role.Type.PARTY)

    fun createDiagram(filePath: String) {

    }
}



