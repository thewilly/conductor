package io.github.thewilly.conductor.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
open class StartUp

    fun main(args: Array<String>) {
        SpringApplication.run(StartUp::class.java, *args)
    }
