package id.renner.backend.controller

import id.renner.backend.model.ServiceTestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    private val services = listOf(
            ServiceTestDto(1, "test-service", "127.0.0.1", "A service for testing."),
            ServiceTestDto(1, "user-service", "255.255.255.255", "Contains user data."),
            ServiceTestDto(1, "some-other-service", "192.168.1.1", "Does stuff(?)."),
            ServiceTestDto(1, "old-service", "0.0.0.0", "At this point nobody knows what this does."),
            ServiceTestDto(1, "test-service-2", "127.127.127.127", "Can never have enough tests")
    )

    @GetMapping("/services")
    fun test(): List<ServiceTestDto> {
        return services
    }
}