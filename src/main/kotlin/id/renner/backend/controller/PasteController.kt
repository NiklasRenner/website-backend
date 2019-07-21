package id.renner.backend.controller

import id.renner.backend.entity.Paste
import id.renner.backend.repository.PasteRepository
import id.renner.backend.util.generateId
import id.renner.backend.util.hostname
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/p")
class PasteController(val repository: PasteRepository) {

    @PostMapping
    fun paste(@RequestBody data: String): ResponseEntity<String> {
        val paste = Paste(generateId(16), data)

        try {
            repository.save(paste)
        } catch (ex: DataIntegrityViolationException) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok("${hostname()}/p/${paste.id}\n")
    }

    @GetMapping("/{id}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun get(@PathVariable("id") id: String): ResponseEntity<String> {
        val paste = repository.findById(id)

        return when (paste.isPresent) {
            true -> ResponseEntity.ok(paste.get().data)
            false -> ResponseEntity.notFound().build()
        }
    }
}