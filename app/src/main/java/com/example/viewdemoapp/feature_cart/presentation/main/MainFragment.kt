package com.example.viewdemoapp.feature_cart.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewdemoapp.R
import com.example.viewdemoapp.databinding.FragmentMainBinding
import com.example.viewdemoapp.feature_cart.presentation.models.ProductDetails
import com.example.viewdemoapp.ui.common.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment :
    Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var _mainAdapter: MainAdapter? = null
    private val mainAdapter get() = _mainAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupUiStateObserver()
    }

    private fun setupUi() {
        if (mainAdapter == null) _mainAdapter = MainAdapter(
            { product, _ -> onProductClicked(product) },
            { categoryId -> onClickedCategory(categoryId) }
        )
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.mainRecyclerView.adapter = mainAdapter

        binding.toolbar.toolbarTitle.text = getString(R.string.viewDemoApp)
        binding.toolbar.btnCart.setOnClickListener { navigate(R.id.ShoppingCartFragment) }
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainUiState.collectLatest { cat ->
                    cat.isLoading?.let {
                        binding.loading.visibility = View.VISIBLE
                    }
                    cat.categorySectionList?.let {
                        binding.loading.visibility = View.GONE
                        mainAdapter?.submitList(cat.categorySectionList)
                    }
                    cat.hasError?.let {
                        Toast.makeText(context, cat.hasError, Toast.LENGTH_SHORT).show()
                    }
                    cat.hasCheckoutOrder?.let {
                        if (it) {
                            binding.toolbar.btnCart.visibility = View.VISIBLE
                        } else {
                            binding.toolbar.btnCart.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mainRecyclerView.adapter = null
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _mainAdapter = null
    }

    private fun onProductClicked(productDetails: ProductDetails) {
        navigate(
            R.id.action_MainFragment_to_ProductDetailsFragment,
            bundleOf("productDetails" to productDetails)
        )
    }

    private fun onClickedCategory(categorySectionId: String) {
        navigate(
            R.id.action_MainFragment_to_CategoryDetailsFragment,
            bundleOf("categorySectionId" to categorySectionId)
        )
    }
}
