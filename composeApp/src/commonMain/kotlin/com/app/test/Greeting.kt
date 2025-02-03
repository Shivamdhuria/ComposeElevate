package com.app.test

import io.github.shivamdhuria.elevate.getGreeting

class Greeting {
    private val platform = getGreeting()
    fun greet(): String {
        return "Hello, ${platform}!"
    }
}