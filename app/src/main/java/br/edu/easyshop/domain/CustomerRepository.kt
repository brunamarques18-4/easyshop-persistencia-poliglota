package br.edu.easyshop.domain

import br.edu.easyshop.data.sql.CustomerEntity
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun observeAll(): Flow<List<CustomerEntity>>
    suspend fun insert(customer: CustomerEntity)
}
