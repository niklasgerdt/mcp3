package mcp

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class restApi {

    @GetMapping("/api")
    fun api(): String {
        return "Hello World!"
    }

    @GetMapping("/foo")
    fun foo(): String {
        return "Hello World!"
    }

    @GetMapping("/.well-known/oauth-protected-resource", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun auth1(): OauthProtectedResource {
        val r = OauthProtectedResource()
        return r
    }

    @GetMapping("/.well-known/oauth-authorization-server",
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE),
        headers = arrayOf("Authorization, Content-type"),
        consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun auth2(): OautAuthorizationServer {
        val r = OautAuthorizationServer()
        return r
    }

    class OauthProtectedResource {
        val resource = "http://localhost:8080/sse"
        val authorization_servers = listOf<String>("https://accounts.google.com")
        val jwks_uri = "https://www.googleapis.com/oauth2/v3/certs"
        val bearer_methods_supported = listOf<String>("header")
    }

    class OautAuthorizationServer {
        val issuer = "https://accounts.google.com"
        val authorization_endpoint = "https://accounts.google.com/o/oauth2/v2/auth"
        val token_endpoint = "https://oauth2.googleapis.com/token"
        val jwks_uri = "https://www.googleapis.com/oauth2/v3/certs"
        val response_types_supported =  listOf<String>("code")
        val subject_types_supported = listOf<String>("public")
        val id_token_signing_alg_values_supported = listOf<String>("RS256")
        val scopes_supported = listOf<String>("openid", "email", "profile")
    }
}