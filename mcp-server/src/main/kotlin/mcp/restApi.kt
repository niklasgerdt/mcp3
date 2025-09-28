package mcp

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class restApi {

    @Value("\${server.port:8080}")
    private lateinit var serverPort: String

    @GetMapping("/api")
    fun api(): String {
        return "Hello World!"
    }

    @GetMapping("/foo")
    fun foo(): String {
        return "Hello World!"
    }

    @GetMapping("/bar")
    fun bar(): String {
        return "bar"
    }

    @GetMapping("/.well-known/oauth-protected-resource", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun auth1(): OauthProtectedResource {
        val baseUrl = "http://localhost:$serverPort"
        val authBaseUrl = "http://localhost:9000"
        return OauthProtectedResource(
            resource = "$baseUrl/sse",
            authorization_servers = listOf(authBaseUrl),
            jwks_uri = "$authBaseUrl/oauth2/jwks",
            bearer_methods_supported = listOf("header", "body", "query")
        )
    }

//    @GetMapping("/.well-known/oauth-protected-resource/sse", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
//    fun sseResource(): OauthProtectedResource {
//        val baseUrl = "http://localhost:$serverPort"
//        val authBaseUrl = "http://localhost:9000"
//        return OauthProtectedResource(
//            resource = "$baseUrl/sse",
//            authorization_servers = listOf(baseUrl),
//            jwks_uri = "$baseUrl/oauth2/jwks",
//            bearer_methods_supported = listOf("header"),
//            resource_type = "server-sent-events",
//            scopes_supported = listOf("sse:read", "sse:subscribe"),
//            resource_documentation = "$baseUrl/docs/sse"
//        )
//    }

    @GetMapping("/.well-known/oauth-authorization-server", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun auth2(): OautAuthorizationServer {
        val baseUrl = "http://localhost:9000"
        val authBaseUrl = "http://localhost:9000"
        return OautAuthorizationServer(
            issuer = baseUrl,
            authorization_endpoint = "$authBaseUrl/oauth2/authorize",
            token_endpoint = "$authBaseUrl/oauth2/token",
            registration_endpoint = "$authBaseUrl/oauth2/register",
            jwks_uri = "$authBaseUrl/oauth2/jwks",
            userinfo_endpoint = "$authBaseUrl/userinfo",
            response_types_supported = listOf("code"),
            grant_types_supported = listOf("authorization_code", "client_credentials"),
            subject_types_supported = listOf("public"),
            id_token_signing_alg_values_supported = listOf("RS256"),
            scopes_supported = listOf("openid", "email", "profile", "user"),
            token_endpoint_auth_methods_supported = listOf("client_secret_basic", "client_secret_post"),
            code_challenge_methods_supported = listOf("S256", "plain")

        )
    }

    data class OauthProtectedResource(
        val resource: String,
        val authorization_servers: List<String>,
        val jwks_uri: String,
        val bearer_methods_supported: List<String>,
        val resource_type: String? = null,
        val scopes_supported: List<String>? = null,
        val resource_documentation: String? = null
    )

    data class OautAuthorizationServer(
        val issuer: String,
        val authorization_endpoint: String,
        val token_endpoint: String,
        val registration_endpoint: String? = null,
        val jwks_uri: String,
        val userinfo_endpoint: String? = null,
        val response_types_supported: List<String>,
        val grant_types_supported: List<String>? = null,
        val subject_types_supported: List<String>,
        val id_token_signing_alg_values_supported: List<String>,
        val scopes_supported: List<String>,
        val token_endpoint_auth_methods_supported: List<String>? = null,
        val code_challenge_methods_supported: List<String>? = null
    )
}