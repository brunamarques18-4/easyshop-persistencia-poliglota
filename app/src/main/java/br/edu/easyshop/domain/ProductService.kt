package br.edu.easyshop.domain

import br.edu.easyshop.data.nosql.ProductDocument
import kotlinx.coroutines.flow.Flow

class ProductService(private val repository: ProductRepository) {
    fun observeProducts(): Flow<List<ProductDocument>> = repository.observeAll()

    suspend fun register(name: String, priceText: String, category: String, attributesText: String) {
        val cleanName = name.trim()
        val price = priceText.replace(',', '.').toDoubleOrNull()
        require(cleanName.isNotBlank()) { "Informe o nome do produto." }
        require(price != null && price > 0) { "Informe um preço maior que zero." }
        repository.insert(ProductDocument(
            name = cleanName,
            price = price,
            category = category.trim().ifBlank { null },
            attributes = parseAttributes(attributesText)
        ))
    }

    suspend fun delete(productId: Long) {
        require(productId > 0) { "Produto inválido." }
        repository.delete(productId)
    }

    private fun parseAttributes(input: String): Map<String, String> = buildMap {
        input.lineSequence().map(String::trim).filter(String::isNotEmpty).forEach { line ->
            val split = line.split(Regex("[:=]"), limit = 2)
            require(split.size == 2 && split[0].trim().isNotEmpty() && split[1].trim().isNotEmpty()) {
                "Use um atributo por linha no formato Chave: Valor."
            }
            put(split[0].trim(), split[1].trim())
        }
    }
}
