package id.renner.backend.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/p")
class PasteController {
    private val map = HashMap<String, String>()

    @PostMapping
    fun paste(@RequestBody data: String): ResponseEntity<String> {

        val id = UUID.randomUUID().toString()
        map[id] = data

        return ResponseEntity.ok("http://localhost:8080/p/$id")
    }

    @GetMapping("/{id}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun get(@PathVariable("id") id: String): ResponseEntity<String> {
        val data = map[id]

        return when (data) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(data)
        }
    }
}