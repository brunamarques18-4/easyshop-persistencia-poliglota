package br.edu.easyshop

import android.app.Application
import br.edu.easyshop.data.nosql.ProductDocumentRepository
import br.edu.easyshop.data.sql.EasyShopDatabase
import br.edu.easyshop.data.sql.CustomerSqlRepository
import br.edu.easyshop.domain.CustomerService
import br.edu.easyshop.domain.ProductService

/** Composition root: wire up the two persistence mechanisms once per application. */
class EasyShopApplication : Application() {
    lateinit var customerService: CustomerService
        private set
    lateinit var productService: ProductService
        private set

    override fun onCreate() {
        super.onCreate()
        customerService = CustomerService(CustomerSqlRepository(EasyShopDatabase.create(this).customerDao()))
        productService = ProductService(ProductDocumentRepository(this))
    }
}
