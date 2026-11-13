package br.edu.easyshop.domain

import android.util.Patterns
import br.edu.easyshop.data.sql.CustomerEntity
import kotlinx.coroutines.flow.Flow

class CustomerService(private val repository: CustomerRepository) {
    fun observeCustomers(): Flow<List<CustomerEntity>> = repository.observeAll()

    suspend fun register(fullName: String, email: String) {
        val cleanName = fullName.trim()
        val cleanEmail = email.trim().lowercase()
        require(cleanName.length >= 3) { "Informe o nome completo do cliente." }
        require(Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) { "Informe um e-mail válido." }
        repository.insert(CustomerEntity(fullName = cleanName, email = cleanEmail))
    }
}
