package id.renner.backend.controller

import id.renner.backend.model.TestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun test(): TestDto {
        return TestDto("Hello!")
    }
}