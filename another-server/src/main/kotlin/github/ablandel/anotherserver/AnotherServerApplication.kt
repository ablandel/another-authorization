package github.ablandel.anotherserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnotherServerApplication

fun main(args: Array<String>) {
    runApplication<AnotherServerApplication>(*args)
}
