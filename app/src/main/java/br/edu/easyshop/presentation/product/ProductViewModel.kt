package br.edu.easyshop.presentation.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.edu.easyshop.EasyShopApplication
import br.edu.easyshop.presentation.SaveFeedback
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val service = (application as EasyShopApplication).productService
    val products = service.observeProducts().asLiveData()
    val feedback = MutableLiveData<SaveFeedback>()

    fun save(name: String, price: String, category: String, attributes: String) = viewModelScope.launch {
        runCatching { service.register(name, price, category, attributes) }
            .onSuccess { feedback.value = SaveFeedback("Produto adicionado ao catálogo.", true) }
            .onFailure { feedback.value = SaveFeedback(it.message ?: "Não foi possível salvar o produto.", false) }
    }

    fun delete(productId: Long) = viewModelScope.launch {
        runCatching { service.delete(productId) }
            .onSuccess { feedback.value = SaveFeedback("Produto removido do catálogo.", false) }
            .onFailure { feedback.value = SaveFeedback(it.message ?: "Não foi possível remover o produto.", false) }
    }
}
