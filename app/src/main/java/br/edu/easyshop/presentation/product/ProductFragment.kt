package br.edu.easyshop.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.easyshop.data.nosql.ProductDocument
import br.edu.easyshop.databinding.FragmentProductsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProductFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by viewModels()
    private val adapter = ProductAdapter(::confirmDeletion)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, state: Bundle?) = with(binding) {
        productsList.layoutManager = LinearLayoutManager(requireContext())
        productsList.adapter = adapter
        saveProduct.setOnClickListener {
            viewModel.save(productName.text.toString(), productPrice.text.toString(),
                productCategory.text.toString(), productAttributes.text.toString())
        }
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submit(it)
            productsCount.text = "${it.size} ${if (it.size == 1) "item" else "itens"}"
        }
        viewModel.feedback.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            if (it.shouldClearForm) {
                productName.text?.clear()
                productPrice.text?.clear()
                productCategory.text?.clear()
                productAttributes.text?.clear()
            }
        }
    }

    private fun confirmDeletion(product: ProductDocument) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Excluir produto?")
            .setMessage("\"${product.name}\" será removido permanentemente do catálogo.")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Excluir") { _, _ -> viewModel.delete(product.id) }
            .show()
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
