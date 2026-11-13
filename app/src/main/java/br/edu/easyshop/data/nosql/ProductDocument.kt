package br.edu.easyshop.data.nosql

/** A schemaless document: each product can contain a distinct set of attributes. */
data class ProductDocument(
    val id: Long = 0,
    val name: String,
    val price: Double,
    val category: String?,
    val attributes: Map<String, String>
)
