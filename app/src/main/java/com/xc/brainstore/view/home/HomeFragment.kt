package com.xc.brainstore.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xc.brainstore.databinding.FragmentHomeBinding
import com.xc.brainstore.view.detail.DetailProductViewModel

class HomeFragment : Fragment() {
//    , ProductAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
//    private lateinit var productAdapter: ProductAdapter

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val detailViewModel: DetailProductViewModel by lazy {
        ViewModelProvider(this)[DetailProductViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        productAdapter = ProductAdapter()
//        productAdapter.setOnItemClickListener(this)
//
//        binding.rvProduct.layoutManager = GridLayoutManager(context, 2)
//        binding.progressBar.visibility = View.GONE
//
//        homeViewModel.productList.observe(this) { productList ->
//            productList?.let {
//                productAdapter.submitList(it)
//            }
//        }
//
//        homeViewModel.isLoading.observe(this, Observer { isLoading ->
//            showLoading(isLoading)
//        })
//
//        homeViewModel.message.observe(this) {message ->
//            message?.let {
//                showToast(it)
//            }
//        }
//
//        homeViewModel.productData.observe(this, Observer { product ->
//            productAdapter.submitList(product)
//        })
//
//        detailViewModel.productDetail.observe(this, Observer { detail ->
//            //
//        })
//
//        homeViewModel.getProduct()
//
//        with(binding) {
//            searchView.setupWithSearchBar(searchBar)
//            searchView
//                .editText
//                .setOnEditorActionListener { _, _, _ ->
//                    val query = searchView.text.toString()
//                    homeViewModel.searchProduct(query)
//                    searchBar.setText(query)
//                    searchView.hide()
//                    true
//                }
//            false
//        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onItemClick(product: Any) {
//        when (product) {
//            is ProductResponse -> {
//                detailViewModel.getProductDetail(product.id)
//            }
//
//            is ItemsItem -> {
//                detailViewModel.getProductDetail(product.id)
//            }
//
//            else -> return
//        }
//    }
}