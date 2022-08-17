package com.example.viewdemoapp.feature_cart.presentation.product_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewdemoapp.databinding.FragmentProductDetailsBinding
import com.example.viewdemoapp.feature_cart.presentation.common.glide.GlideApp
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails
import com.example.viewdemoapp.feature_cart.presentation.models.toProductDetails
import com.example.viewdemoapp.ui.common.popBackStack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        // Moze samo Id
        arguments?.getParcelable<ProductDetails>("productDetails").let { setupViewModel(it!!) }
    }

    private fun setupUi() {
        binding.toolbar.btnBack.setOnClickListener { popBackStack() }
    }

    private fun setupViewModel(productDetails: ProductDetails) {
        setupUiStateObserver()
        productDetailsViewModel.setup(productDetails)
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productDetailsViewModel.productDetailsUiState.collect { uiState ->
                    uiState.product?.let { updateUI(it.toProductDetails()) }
                }
            }
        }
    }

    private fun updateUI(productDetails: ProductDetails) {
        binding.addToCartCad.currentAmountCard.text = productDetails.quantity.toString()

        binding.addToCartCad.add.setOnClickListener {
            productDetailsViewModel.addToCart(productDetails)
        }
        binding.addToCartCad.remove.setOnClickListener {
            productDetailsViewModel.removeFromCart(productDetails)
        }

        GlideApp.with(binding.root)
            .load(productDetails.image)
            .fitCenter()
            .into(binding.imageButton)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
