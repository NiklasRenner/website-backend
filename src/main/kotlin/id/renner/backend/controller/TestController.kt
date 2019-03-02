package id.renner.backend.controller

import id.renner.backend.model.ServiceTestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    private val services = listOf(
            ServiceTestDto(1, "test-service", "127.0.0.1", "A service for testing."),
            ServiceTestDto(2, "user-service", "255.255.255.255", "Contains user data."),
            ServiceTestDto(3, "some-other-service", "192.168.1.1", "Does stuff(?)."),
            ServiceTestDto(4, "old-service", "0.0.0.0", "At this point nobody knows what this does."),
            ServiceTestDto(5, "test-service-2", "127.127.127.127", "Can never have enough tests"),
            ServiceTestDto(6, "cdeploy-service", "13.37.13.37", "Wait this is in prod?")
    )

    @GetMapping("/services")
    fun test(): List<ServiceTestDto> {
        return services
    }
}