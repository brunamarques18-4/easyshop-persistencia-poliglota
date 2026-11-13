package br.edu.easyshop.data.nosql

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * ObjectBox entity. The attributes are stored inside a JSON document, so new
 * product characteristics require no column, table, or database migration.
 */
@Entity
data class ProductDocumentEntity(
    @Id var id: Long = 0,
    var name: String = "",
    var price: Double = 0.0,
    var category: String? = null,
    var attributesJson: String = "{}"
)
