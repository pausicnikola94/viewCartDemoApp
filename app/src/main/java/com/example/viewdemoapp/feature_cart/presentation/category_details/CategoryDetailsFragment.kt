package com.example.viewdemoapp.feature_cart.presentation.category_details

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
import com.example.viewdemoapp.R
import com.example.viewdemoapp.core.util.CustomGridLayoutManager
import com.example.viewdemoapp.core.util.CustomItemDecoratorNormalRV
import com.example.viewdemoapp.databinding.FragmentCategoryDetailsBinding
import com.example.viewdemoapp.feature_cart.presentation.common.product.ProductsAdapter
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails
import com.example.viewdemoapp.ui.common.navigate
import com.example.viewdemoapp.ui.common.popBackStack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryDetailsFragment : Fragment() {

    private var _binding: FragmentCategoryDetailsBinding? = null
    private val binding get() = _binding!!
    private val categoryDetailsViewModel: CategoryDetailsViewModel by viewModels()
    private var _productsAdapter: ProductsAdapter? = null
    private val productsAdapter get() = _productsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("categorySectionId").let { setupViewModel(it!!) }
    }

    private fun productClicked(productDetails: ProductDetails) {
        navigate(
            R.id.action_CategoryDetailsFragment_to_ProductDetailsFragment,
            bundleOf("productDetails" to productDetails)
        )
    }

    private fun removeClicked(productDetails: ProductDetails, position: Int) {
        productsAdapter?.setClickedPosition(position)
        categoryDetailsViewModel.removeFromCart(productDetails)
    }

    private fun addClicked(productDetails: ProductDetails, position: Int) {
        productsAdapter?.setClickedPosition(position)
        categoryDetailsViewModel.addToCart(productDetails)
    }

    private fun setupViewModel(categorySectionId: String) {
        categoryDetailsViewModel.setup(categorySectionId)
        setupUi(categorySectionId)
        setupUiStateObserver()
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryDetailsViewModel.categoryUiState.collectLatest { cat ->
                    cat.hasError?.let {
                        Toast.makeText(context, cat.hasError, Toast.LENGTH_SHORT).show()
                    }

                    cat.products?.let {
                        productsAdapter?.updateValues(it)
                    }
                }
            }
        }
    }

    private fun setupUi(categorySectionId: String) {
        if (productsAdapter == null) _productsAdapter = ProductsAdapter(
            { product, position -> addClicked(product, position) },
            { product, position -> removeClicked(product, position) }
        ) { productClicked(it) }
        binding.categoryRecyclerView.addItemDecoration(CustomItemDecoratorNormalRV(30, CustomItemDecoratorNormalRV.GRID))
        binding.categoryRecyclerView.layoutManager = CustomGridLayoutManager(context, 2)
        binding.categoryRecyclerView.adapter = productsAdapter

        binding.toolbar.toolbarTitle.text = categorySectionId.replaceFirstChar { it.uppercase() }
        binding.toolbar.btnBack.setOnClickListener { popBackStack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _productsAdapter = null
        _binding = null
    }
}
