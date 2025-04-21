package github.ablandel.anotherserver.shared.repository

import dev.openfga.sdk.api.client.OpenFgaClient
import dev.openfga.sdk.api.client.model.ClientCheckRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Repository

private val logger = KotlinLogging.logger {}

@Repository
class OpenFGARepository(
    private val openFGAClient: OpenFgaClient,
) {
    fun check(
        userTypeAndObjectIdentifier: Pair<String, String>,
        relation: String,
        objectTypeAndObjectIdentifier: Pair<String, String>,
    ): Boolean {
        val request =
            ClientCheckRequest()
                .user("${userTypeAndObjectIdentifier.first}:${userTypeAndObjectIdentifier.second}")
                .relation(relation)
                ._object("${objectTypeAndObjectIdentifier.first}:${objectTypeAndObjectIdentifier.second}")
        logger.info {
            "...sending a check request to the OpenFGA instance. Tuple: User: `${userTypeAndObjectIdentifier.first}:${userTypeAndObjectIdentifier.second}` - Relation: `$relation` - Object: `${objectTypeAndObjectIdentifier.first}:${objectTypeAndObjectIdentifier.second}`"
        }
        return openFGAClient.check(request).get().allowed ?: false
    }
}
