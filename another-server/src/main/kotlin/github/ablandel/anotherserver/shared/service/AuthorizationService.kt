package github.ablandel.anotherserver.shared.service

import github.ablandel.anotherserver.shared.repository.OpenFGARepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AuthorizationService(
    private val openFGARepository: OpenFGARepository,
) {
    private fun getUserIdFromToken(): String {
        val userId = SecurityContextHolder.getContext().authentication.name
        logger.info { "...extraction of the user ID from the token - user ID: `$userId`" }
        return userId
    }

    fun isAllowedToAccessToPlace(type: String): Boolean {
        val userId = getUserIdFromToken()
        return openFGARepository.check(
            Pair("user", userId),
            "participant",
            Pair("place", type),
        )
    }
}
