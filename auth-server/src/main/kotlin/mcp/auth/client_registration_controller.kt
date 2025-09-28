package mcp.auth

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class ClientRegistrationController(
    private val registeredClientRepository: RegisteredClientRepository
) {

    @PostMapping(
        "/oauth2/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun registerClient(@RequestBody request: ClientRegistrationRequest): ResponseEntity<ClientRegistrationResponse> {
        try {
            // Generate client credentials
            val clientId = "client-" + UUID.randomUUID().toString()
            val clientSecret = "secret-" + UUID.randomUUID().toString()

            // Build the registered client
            val registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret("{noop}$clientSecret") // {noop} means no password encoder
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUris { uris ->
                    request.redirectUris?.forEach { uri -> uris.add(uri) }
                }
                .scopes { scopes ->
                    // Add default scopes or use requested scopes
                    request.scope?.split(" ")?.forEach { scope -> scopes.add(scope) }
                        ?: run {
                            scopes.add("openid")
                            scopes.add("profile")
                            scopes.add("email")
                        }
                }
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build()

            // Save the client
            registeredClientRepository.save(registeredClient)

            // Return the registration response
            val response = ClientRegistrationResponse(
                clientId = clientId,
                clientSecret = clientSecret,
                clientIdIssuedAt = Instant.now().epochSecond,
                clientSecretExpiresAt = 0, // 0 means it doesn't expire
                redirectUris = request.redirectUris ?: emptyList(),
                grantTypes = listOf("authorization_code", "client_credentials"),
                responseTypes = listOf("code"),
                clientName = request.clientName,
                scope = request.scope ?: "openid profile email"
            )

            return ResponseEntity.status(HttpStatus.CREATED).body(response)

        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    data class ClientRegistrationRequest(
        @JsonProperty("redirect_uris")
        val redirectUris: List<String>? = null,
        @JsonProperty("client_name")
        val clientName: String? = null,
        @JsonProperty("client_uri")
        val clientUri: String? = null,
        @JsonProperty("logo_uri")
        val logoUri: String? = null,
        @JsonProperty("scope")
        val scope: String? = null,
        @JsonProperty("contacts")
        val contacts: List<String>? = null,
        @JsonProperty("tos_uri")
        val tosUri: String? = null,
        @JsonProperty("policy_uri")
        val policyUri: String? = null,
        @JsonProperty("jwks_uri")
        val jwksUri: String? = null,
        @JsonProperty("software_id")
        val softwareId: String? = null,
        @JsonProperty("software_version")
        val softwareVersion: String? = null
    )

    data class ClientRegistrationResponse(
        @JsonProperty("client_id")
        val clientId: String,
        @JsonProperty("client_secret")
        val clientSecret: String,
        @JsonProperty("client_id_issued_at")
        val clientIdIssuedAt: Long,
        @JsonProperty("client_secret_expires_at")
        val clientSecretExpiresAt: Long,
        @JsonProperty("redirect_uris")
        val redirectUris: List<String>,
        @JsonProperty("grant_types")
        val grantTypes: List<String>,
        @JsonProperty("response_types")
        val responseTypes: List<String>,
        @JsonProperty("client_name")
        val clientName: String?,
        @JsonProperty("scope")
        val scope: String
    )
}
