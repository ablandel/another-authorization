package github.ablandel.anotherserver.place.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotAllowedToAccessToPlace(
    type: String,
) : ResponseStatusException(
        HttpStatus.FORBIDDEN,
        "You are not allowed to access to the `$type` place",
    )
