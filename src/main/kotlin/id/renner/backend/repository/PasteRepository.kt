package id.renner.backend.repository

import id.renner.backend.entity.Paste
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PasteRepository : CrudRepository<Paste, String>