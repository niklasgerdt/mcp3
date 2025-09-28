package mcp.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Api {

    @GetMapping("/api")
    fun api(): String {
        return "api"
    }

    @GetMapping("/foo")
    fun foo(): String {
        return "foo"
    }
}