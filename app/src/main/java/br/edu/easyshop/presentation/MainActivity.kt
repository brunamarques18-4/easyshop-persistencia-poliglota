package br.edu.easyshop.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.edu.easyshop.databinding.ActivityMainBinding
import br.edu.easyshop.presentation.customer.CustomerFragment
import br.edu.easyshop.presentation.product.ProductFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int) = when (position) {
                0 -> ProductFragment()
                else -> CustomerFragment()
            }
        }
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = if (position == 0) "Produtos (NoSQL)" else "Clientes (SQL)"
        }.attach()
    }
}
