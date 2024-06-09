package com.xc.brainstore.view.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.search.SearchView
import com.xc.brainstore.R
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.ProductsItem
import com.xc.brainstore.data.remote.response.SellerResponse
import com.xc.brainstore.databinding.FragmentHomeBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ProductViewModelFactory
import com.xc.brainstore.view.adapter.ProductAdapter
import com.xc.brainstore.view.detail.DetailProductActivity
import com.xc.brainstore.view.detail.DetailProductViewModel

class HomeFragment : Fragment(), ProductAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private var productDetail: ProductResponseItem? = null
    private var storeDetail: SellerResponse? = null
    private val repository by lazy { Injection.provideProductRepository(requireActivity()) }

    private val homeViewModel: HomeViewModel by lazy {
        val factory = ProductViewModelFactory(repository)
        ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    private val detailViewModel: DetailProductViewModel by lazy {
        val factory = ProductViewModelFactory(repository)
        ViewModelProvider(this, factory)[DetailProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        setupCardViewListeners()

        if (homeViewModel.productList.value.isNullOrEmpty()) {
            homeViewModel.getProduct()
        }

        with(binding) {
            var searched = false
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val query = searchView.text.toString()
                        homeViewModel.searchProduct(query)
                        searchBar.setText(query)
                        searchView.hide()
                        Handler(Looper.getMainLooper()).postDelayed({
                            prevIcon.visibility = View.VISIBLE
                        }, 300)
                        searched = true
                        true
                    } else {
                        false
                    }
                }

            searchView.addTransitionListener { _, _, newState ->
                if (newState == SearchView.TransitionState.SHOWN) {
                    prevIcon.visibility = View.GONE
                } else if (newState == SearchView.TransitionState.HIDDEN) {
                    if (searched) {
                        prevIcon.visibility = View.VISIBLE
                    } else {
                        prevIcon.visibility = View.GONE
                    }
                }
            }

            prevIcon.setOnClickListener {
                searchBar.setText("")
                prevIcon.visibility = View.GONE
                searchBar.clearFocus()
                cardView.visibility = View.VISIBLE
                homeViewModel.getProduct()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter()
        productAdapter.setOnItemClickListener(this)

        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(context, 2)
        binding.progressBar.visibility = View.GONE
    }

    private fun observeViewModel() {
        homeViewModel.productList.observe(viewLifecycleOwner) { productList ->
            productList?.let {
                productAdapter.submitList(it)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        homeViewModel.message.observe(viewLifecycleOwner) { message ->
            message?.let {
                showToast(it)
            }
        }

        homeViewModel.productData.observe(viewLifecycleOwner) { product ->
            productAdapter.submitList(product)
            binding.cardView.visibility = View.GONE
        }

        detailViewModel.productDetail.observe(viewLifecycleOwner) { detail ->
            detailViewModel.storeDetail.observe(viewLifecycleOwner) { storeDetails ->
                productDetail = detail
                storeDetail = storeDetails
                checkIfReadyToNavigate()
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        detailViewModel.message.observe(viewLifecycleOwner) { message ->
            message?.let {
                showToast(it)
            }
        }
    }

    private fun checkIfReadyToNavigate() {
        if (productDetail != null && storeDetail != null) {
            navigateToDetailFragment(productDetail!!, storeDetail!!)
        }
    }

    private fun navigateToDetailFragment(
        productDetail: ProductResponseItem,
        storeDetail: SellerResponse
    ) {
        val intent = Intent(requireContext(), DetailProductActivity::class.java).apply {
            putExtra("PRODUCT_DATA", productDetail)
            putExtra("STORE_DATA", storeDetail)
        }
        startActivity(intent)
    }

    private fun setupCardViewListeners() {

        val allCV = binding.allCardView
        val newestCV = binding.newestCardView
        val popularCV = binding.popularCardView
        val locationCV = binding.locationCardView

        allCV.setOnClickListener {
            updateCardSelection(allCV, binding.all, true)
            updateCardSelection(newestCV, binding.newest, false)
            updateCardSelection(popularCV, binding.popular, false)
            updateCardSelection(locationCV, binding.location, false)

            homeViewModel.getProduct()
        }

        newestCV.setOnClickListener {
            updateCardSelection(allCV, binding.all, false)
            updateCardSelection(newestCV, binding.newest, true)
            updateCardSelection(popularCV, binding.popular, false)
            updateCardSelection(locationCV, binding.location, false)

            homeViewModel.getNewestProduct()
        }

        popularCV.setOnClickListener {
            updateCardSelection(allCV, binding.all, false)
            updateCardSelection(newestCV, binding.newest, false)
            updateCardSelection(popularCV, binding.popular, true)
            updateCardSelection(locationCV, binding.location, false)

            homeViewModel.getPopularProduct()
        }

        locationCV.setOnClickListener {
            updateCardSelection(allCV, binding.all, false)
            updateCardSelection(newestCV, binding.newest, false)
            updateCardSelection(popularCV, binding.popular, false)
            updateCardSelection(locationCV, binding.location, true)

            homeViewModel.getLocationProduct()
        }

    }

    private fun updateCardSelection(cardView: CardView, textView: TextView, isSelected: Boolean) {
        if (isSelected) {
            cardView.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.main_color))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_g))
        } else {
            cardView.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white_g))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(product: Any) {
        when (product) {
            is ProductResponseItem -> {
                detailViewModel.getProductDetail(product.productId)
                detailViewModel.getStoreDetail(product.storeId)
            }

            is ProductsItem -> {
                detailViewModel.getProductDetail(product.productId)
                detailViewModel.getStoreDetail(product.storeId)
            }

            else -> return
        }
    }
}