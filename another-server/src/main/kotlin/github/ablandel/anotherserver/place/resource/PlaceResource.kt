package github.ablandel.anotherserver.place.resource

import github.ablandel.anotherserver.place.dto.Place
import github.ablandel.anotherserver.place.service.PlaceService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class PlaceResource(
    private val placeService: PlaceService,
) {
    @GetMapping("/v1/places/{type}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun findByType(
        @PathVariable("type") type: String,
    ): Place {
        logger.info { "New request received - requesting the `$type` place..." }
        return placeService.findByType(type)
    }
}
