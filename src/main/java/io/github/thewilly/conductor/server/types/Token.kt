package io.github.thewilly.conductor.server.types

class Token private constructor() {

    private val token: String? = null

    companion object {

        fun builder(): String? {
            return Token().token
        }
    }
}
