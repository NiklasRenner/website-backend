package id.renner.backend.controller

import id.renner.backend.model.ServiceDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class TestController {
    private val services = listOf(
            ServiceDto(1, "test-service", "127.0.0.1", "A service for testing."),
            ServiceDto(2, "user-service", "255.255.255.255", "Contains user data."),
            ServiceDto(3, "some-other-service", "192.168.1.1", "Does stuff(?)."),
            ServiceDto(4, "old-service", "0.0.0.0", "At this point nobody knows what this does."),
            ServiceDto(5, "test-service-2", "127.127.127.127", "Can never have enough tests"),
            ServiceDto(6, "deploy-service", "13.37.13.37", "Wait this is in prod?")
    )

    @GetMapping("/services")
    fun getServices() = services

    @GetMapping("/locked")
    fun locked() = services.take(3)

    @GetMapping("/ip")
    fun ip(request: HttpServletRequest) = request.getHeader("X-Forwarded-For") ?: request.remoteAddr

    @GetMapping("/")
    fun index() = "OK"
}