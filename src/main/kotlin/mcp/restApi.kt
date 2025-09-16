package mcp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class restApi {

    @GetMapping("/api")
    fun api(): String {
        return "Hello World!"
    }
}