package github.ablandel.anotherclient.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient

private val logger = KotlinLogging.logger {}

@Service
class PlaceService(
    private val restClient: RestClient,
) {
    fun findOpenPlace() = findPlaceByType("open")

    fun findRestrictedPlace() = findPlaceByType("restricted")

    fun findUnknownPlace() = findPlaceByType("unknown")

    private fun findPlaceByType(type: String) {
        logger.info { "Fetching the `$type` place from the `another-server` instance..." }
        try {
            restClient
                .get()
                .uri("/api/v1/places/$type")
                .retrieve()
                .body(String::class.java)

            logger.info {
                "...the `$type` place has been successfully fetched! 🌟"
            }
        } catch (_: HttpClientErrorException.BadRequest) {
            logger.error {
                "...the `$type` place fetch failed. Check the request parameters! 🤔"
            }
        } catch (_: HttpClientErrorException.Unauthorized) {
            logger.error {
                "...the `$type` place fetch failed. The client is not authorized! 🛡️"
            }
        } catch (_: HttpClientErrorException.Forbidden) {
            logger.error {
                "...the `$type` place fetch failed. The client is not allowed to access to the place! 🛡️"
            }
        } catch (e: Exception) {
            logger.error {
                "...the `$type` place fetch failed. Exception: `$e`"
            }
        }
    }
}
