package com.example.viewdemoapp.feature_cart.presentation.shopping_cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewdemoapp.R
import com.example.viewdemoapp.core.util.CustomItemDecoratorNormalRV
import com.example.viewdemoapp.databinding.FragmentShoppingCartBinding
import com.example.viewdemoapp.feature_cart.presentation.common.product.ProductsAdapter
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails
import com.example.viewdemoapp.ui.common.navigate
import com.example.viewdemoapp.ui.common.popBackStack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingCartFragment : Fragment() {

    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!
    private var _productsAdapter: ProductsAdapter? = null
    private val productsAdapter get() = _productsAdapter
    private val shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupUiStateObserver()
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shoppingCartViewModel.shoppingCartUiState.collectLatest { cart ->
                    cart.hasError?.let {
                        Toast.makeText(context, cart.hasError, Toast.LENGTH_SHORT).show()
                    }
                    if (cart.products == null) return@collectLatest
                    if (cart.products.isEmpty()) {
                        popBackStack()
                    } else {
                        productsAdapter?.updateValues(cart.products)
                    }
                }
            }
        }
    }

    private fun setupUi() {
        if (productsAdapter == null) _productsAdapter = ProductsAdapter(
            { product, position -> addClicked(product, position) },
            { product, position -> removeClicked(product, position) },
            { productClicked(it) }
        )
        binding.shoppingCartRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        binding.shoppingCartRecyclerView.adapter = productsAdapter
        binding.shoppingCartRecyclerView.addItemDecoration(CustomItemDecoratorNormalRV(15, CustomItemDecoratorNormalRV.VERTICAL))

        binding.toolbar.toolbarTitle.text = getString(R.string.shoppingCart)
        binding.toolbar.btnBack.setOnClickListener { popBackStack() }
    }

    private fun productClicked(productDetails: ProductDetails) {
        navigate(
            R.id.action_ShoppingCartFragment_to_ProductDetailsFragment,
            bundleOf("productDetails" to productDetails)
        )
    }

    private fun removeClicked(productDetails: ProductDetails, position: Int) {
        productsAdapter?.setClickedPosition(position)
        shoppingCartViewModel.removeFromCart(productDetails)
    }

    private fun addClicked(productDetails: ProductDetails, position: Int) {
        productsAdapter?.setClickedPosition(position)
        shoppingCartViewModel.addToCart(productDetails)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _productsAdapter = null
    }
}
