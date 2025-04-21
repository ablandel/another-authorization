package github.ablandel.anotherclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class AnotherClientApplication

fun main(args: Array<String>) {
    runApplication<AnotherClientApplication>(*args)
}
