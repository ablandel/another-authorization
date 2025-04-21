package github.ablandel.anotherserver.place.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class PlaceDoesNotExist(
    type: String,
) : ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "The `$type` place does not exist",
    )
