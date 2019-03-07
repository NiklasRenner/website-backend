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
    private val map = HashMap<String, String>()

    @PostMapping
    fun paste(@RequestBody data: String, request: HttpServletRequest): ResponseEntity<String> {
        val id = UUID.randomUUID().toString()
        map[id] = data

        val urlBuilder = StringBuilder()
        urlBuilder.append(request.requestURL.toString())
        if (urlBuilder.lastIndexOf("/") != urlBuilder.length - 1) {
            urlBuilder.append("/")
        }
        urlBuilder.append(id)
                .append('\n')

        return ResponseEntity.ok(urlBuilder.toString())
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