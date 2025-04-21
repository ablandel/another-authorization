package github.ablandel.anotherclient.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfig(
    @Value("\${another-server.base-url}") private val baseUrl: String,
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val authorizedClientService: OAuth2AuthorizedClientService,
) {
    @Bean
    fun restClient(): RestClient {
        val authorizedClientManager =
            AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService,
            ).apply {
                setAuthorizedClientProvider(
                    OAuth2AuthorizedClientProviderBuilder
                        .builder()
                        .clientCredentials()
                        .build(),
                )
            }

        val interceptor =
            ClientHttpRequestInterceptor { request, body, execution ->
                val authorizeRequest =
                    OAuth2AuthorizeRequest
                        .withClientRegistrationId("keycloak")
                        .principal("rest-client")
                        .build()
                val authorizedClient = authorizedClientManager.authorize(authorizeRequest)
                val token = authorizedClient?.accessToken?.tokenValue
                if (token != null) {
                    request.headers.add("Authorization", "Bearer $token")
                }
                execution.execute(request, body)
            }

        return RestClient
            .builder()
            .baseUrl(baseUrl)
            .requestInterceptor(interceptor)
            .build()
    }
}
