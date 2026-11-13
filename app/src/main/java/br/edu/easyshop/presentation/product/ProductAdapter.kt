package br.edu.easyshop.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.easyshop.data.nosql.ProductDocument
import br.edu.easyshop.databinding.ItemProductBinding
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val onDelete: (ProductDocument) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var items: List<ProductDocument> = emptyList()
    fun submit(items: List<ProductDocument>) { this.items = items; notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], onDelete)

    class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDocument, onDelete: (ProductDocument) -> Unit) = with(binding) {
            productName.text = product.name
            productPrice.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(product.price)
            productCategory.text = product.category?.let { "Categoria: $it" } ?: "Sem categoria"
            productAttributes.text = if (product.attributes.isEmpty()) "Sem atributos adicionais" else
                product.attributes.entries.joinToString(" • ") { "${it.key}: ${it.value}" }
            deleteProduct.setOnClickListener { onDelete(product) }
        }
    }
}
