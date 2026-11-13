package br.edu.easyshop.domain

import br.edu.easyshop.data.nosql.ProductDocument
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeAll(): Flow<List<ProductDocument>>
    suspend fun insert(product: ProductDocument)
    suspend fun delete(productId: Long)
}
