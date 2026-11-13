package br.edu.easyshop.presentation.customer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.edu.easyshop.EasyShopApplication
import br.edu.easyshop.presentation.SaveFeedback
import kotlinx.coroutines.launch
import android.database.sqlite.SQLiteConstraintException

class CustomerViewModel(application: Application) : AndroidViewModel(application) {
    private val service = (application as EasyShopApplication).customerService
    val customers = service.observeCustomers().asLiveData()
    val feedback = MutableLiveData<SaveFeedback>()

    fun save(name: String, email: String) = viewModelScope.launch {
        runCatching { service.register(name, email) }
            .onSuccess { feedback.value = SaveFeedback("Cliente salvo com segurança.", true) }
            .onFailure {
                feedback.value = SaveFeedback(
                    if (it is SQLiteConstraintException) "Este e-mail já está cadastrado." else
                        (it.message ?: "Não foi possível salvar o cliente."), false
                )
            }
    }
}
