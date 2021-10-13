package common

interface DSL<T> {
    operator fun invoke(function: T.() -> Unit): T
}

interface Diagram {
    infix fun export(filePath: String)
}