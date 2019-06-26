package id.renner.backend.controller

import id.renner.backend.util.generateId
import id.renner.backend.util.hostname
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/p")
class PasteController {
    private val map = HashMap<String, String>()

    @PostMapping
    fun paste(@RequestBody data: String): ResponseEntity<String> {
        val id = generateId(8)
        map[id] = data

        return ResponseEntity.ok("${hostname()}/p/$id\n")
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