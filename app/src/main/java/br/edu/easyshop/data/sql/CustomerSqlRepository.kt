package br.edu.easyshop.data.sql

import br.edu.easyshop.domain.CustomerRepository
import kotlinx.coroutines.flow.Flow

class CustomerSqlRepository(private val dao: CustomerDao) : CustomerRepository {
    override fun observeAll(): Flow<List<CustomerEntity>> = dao.observeAll()
    override suspend fun insert(customer: CustomerEntity) = dao.insert(customer)
}
