package br.edu.easyshop.presentation.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.easyshop.data.sql.CustomerEntity
import br.edu.easyshop.databinding.ItemCustomerBinding

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    private var items: List<CustomerEntity> = emptyList()
    fun submit(items: List<CustomerEntity>) { this.items = items; notifyDataSetChanged() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(private val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: CustomerEntity) = with(binding) {
            customerName.text = customer.fullName
            customerEmail.text = customer.email
            customerInitial.text = customer.fullName.firstOrNull()?.uppercase() ?: "?"
        }
    }
}
