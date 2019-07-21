package id.renner.backend.entity

import id.renner.backend.util.NoArg
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "bucket")
data class Paste(@Id val id: String, val data: String)