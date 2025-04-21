package github.ablandel.anotherclient.scheduler

import github.ablandel.anotherclient.service.PlaceService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RequestScheduler(
    private val placeService: PlaceService,
) {
    @Scheduled(fixedRate = 18_000)
    fun findOpenPlace() = placeService.findOpenPlace()

    @Scheduled(fixedRate = 18_000, initialDelay = 6_000)
    fun findRestrictedPlace() = placeService.findRestrictedPlace()

    @Scheduled(fixedRate = 18_000, initialDelay = 12_000)
    fun findUnknownPlace() = placeService.findUnknownPlace()
}
