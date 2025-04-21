package github.ablandel.anotherserver.config

import dev.openfga.sdk.api.client.OpenFgaClient
import dev.openfga.sdk.api.configuration.ClientConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenFGAConfig(
    @Value("\${openfga.base-url}") private val baseUrl: String,
    @Value("\${openfga.store-id}") private val storeId: String,
    @Value("\${openfga.authorization-model-id}") private val authorizationModelId: String,
) {
    @Bean
    fun openFGAClient(): OpenFgaClient {
        val clientConfiguration =
            ClientConfiguration()
                .apiUrl(baseUrl)
                .storeId(storeId)
                .authorizationModelId(authorizationModelId)
        return OpenFgaClient(clientConfiguration)
    }
}
