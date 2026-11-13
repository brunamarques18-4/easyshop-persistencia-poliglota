package br.edu.easyshop.data.nosql

import android.content.Context
import br.edu.easyshop.domain.ProductRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.Box
import io.objectbox.BoxStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

/** Repository exclusively responsible for access to the ObjectBox NoSQL store. */
class ProductDocumentRepository(context: Context) : ProductRepository {
    private val gson = Gson()
    private val attributesType = object : TypeToken<Map<String, String>>() {}.type
    private val store: BoxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
    private val box: Box<ProductDocumentEntity> = store.boxFor(ProductDocumentEntity::class.java)
    private val products = MutableStateFlow(loadAll())

    override fun observeAll(): Flow<List<ProductDocument>> = products

    override suspend fun insert(product: ProductDocument) = withContext(Dispatchers.IO) {
        box.put(ProductDocumentEntity(
            name = product.name,
            price = product.price,
            category = product.category,
            attributesJson = gson.toJson(product.attributes)
        ))
        products.value = loadAll()
    }

    override suspend fun delete(productId: Long) = withContext(Dispatchers.IO) {
        box.remove(productId)
        products.value = loadAll()
    }

    private fun loadAll(): List<ProductDocument> = box.all
        .sortedBy { it.name.lowercase() }
        .map { entity ->
            ProductDocument(
                id = entity.id,
                name = entity.name,
                price = entity.price,
                category = entity.category,
                attributes = runCatching {
                    gson.fromJson<Map<String, String>>(entity.attributesJson, attributesType) ?: emptyMap()
                }.getOrDefault(emptyMap())
            )
        }
}
