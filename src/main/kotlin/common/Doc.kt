package common

import java.io.File


interface Doc {
    fun buildDocContent(): String

    infix fun export(filePath: String) {
        generateDoc(filePath)
    }

    private fun generateDoc(filePath: String) {
        val content = buildDocContent()
        println(
            """
            |================================
            |   $content
            |================================
            |   $filePath
        """.trimMargin()
        )
        File(filePath).apply { parentFile.mkdirs() }.writeText(content)
    }
}