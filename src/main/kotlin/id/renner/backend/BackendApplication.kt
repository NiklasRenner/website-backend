package id.renner.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class BackendApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
