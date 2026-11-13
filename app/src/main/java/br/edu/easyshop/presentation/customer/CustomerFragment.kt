package br.edu.easyshop.presentation.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.easyshop.databinding.FragmentCustomersBinding

class CustomerFragment : Fragment() {
    private var _binding: FragmentCustomersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CustomerViewModel by viewModels()
    private val adapter = CustomerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentCustomersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, state: Bundle?) = with(binding) {
        customersList.layoutManager = LinearLayoutManager(requireContext())
        customersList.adapter = adapter
        saveCustomer.setOnClickListener { viewModel.save(customerName.text.toString(), customerEmail.text.toString()) }
        viewModel.customers.observe(viewLifecycleOwner) {
            adapter.submit(it)
            customersCount.text = "${it.size} ${if (it.size == 1) "pessoa" else "pessoas"}"
        }
        viewModel.feedback.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            if (it.shouldClearForm) {
                customerName.text?.clear()
                customerEmail.text?.clear()
            }
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
