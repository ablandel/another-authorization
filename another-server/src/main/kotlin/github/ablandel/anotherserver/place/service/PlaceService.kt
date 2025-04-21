package github.ablandel.anotherserver.place.service

import github.ablandel.anotherserver.place.dto.OpenPlace
import github.ablandel.anotherserver.place.dto.Place
import github.ablandel.anotherserver.place.dto.PlaceTypeEnum
import github.ablandel.anotherserver.place.dto.RestrictedPlace
import github.ablandel.anotherserver.place.exception.NotAllowedToAccessToPlace
import github.ablandel.anotherserver.place.exception.PlaceDoesNotExist
import github.ablandel.anotherserver.shared.service.AuthorizationService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class PlaceService(
    private val authorizationService: AuthorizationService,
) {
    fun findByType(type: String): Place {
        if (type == PlaceTypeEnum.OPEN.type) {
            logger.info { "...the `$type` place has been found and there is no restriction applied!" }
            return OpenPlace()
        } else if (type == PlaceTypeEnum.RESTRICTED.type) {
            if (authorizationService.isAllowedToAccessToPlace(type)) {
                logger.info { "...the `$type` place has been found and the user is allowed to access to it!" }
                return RestrictedPlace()
            }
            logger.info { "...the user is not allowed to access to the `$type` place!" }
            throw NotAllowedToAccessToPlace(type)
        }
        logger.info { "...the `$type` place does not exist!" }
        throw PlaceDoesNotExist(type = type)
    }
}
