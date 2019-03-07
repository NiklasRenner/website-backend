package id.renner.backend.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/p")
class PasteController {
    //TODO can this be done dynamically?(can't use normal request url because of nginx ssl proxy)
    private val hostname = "https://dev.renner.id/p/"
    private val map = HashMap<String, String>()

    @PostMapping
    fun paste(@RequestBody data: String, request: HttpServletRequest): ResponseEntity<String> {
        val id = UUID.randomUUID().toString()
        map[id] = data

        return ResponseEntity.ok(hostname + id)
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