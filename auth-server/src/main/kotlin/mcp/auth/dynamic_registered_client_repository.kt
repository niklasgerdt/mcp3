package mcp.auth

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import java.util.concurrent.ConcurrentHashMap

class DynamicRegisteredClientRepository : RegisteredClientRepository {
    
    private val clients = ConcurrentHashMap<String, RegisteredClient>()
    
    override fun save(registeredClient: RegisteredClient) {
        clients[registeredClient.clientId] = registeredClient
    }

    override fun findById(id: String): RegisteredClient? {
        return clients.values.find { it.id == id }
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        return clients[clientId]
    }
}
