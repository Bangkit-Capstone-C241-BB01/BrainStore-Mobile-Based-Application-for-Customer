package com.xc.brainstore.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.xc.brainstore.R
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.local.room.FavoriteDatabase
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.ProductsItem
import com.xc.brainstore.data.repository.FavoriteRepository
import com.xc.brainstore.databinding.FragmentDetailProductBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ProductViewModelFactory
import com.xc.brainstore.view.ViewModelFactory2
import com.xc.brainstore.view.favorites.FavoriteViewModel

class DetailProductFragment : Fragment() {
    private lateinit var binding: FragmentDetailProductBinding
    private val repository by lazy { Injection.provideProductRepository(requireActivity()) }

    private val detailViewModel: DetailProductViewModel by lazy {
        val factory = ProductViewModelFactory(repository)
        ViewModelProvider(this, factory)[DetailProductViewModel::class.java]
    }

    private val favoriteViewModel: FavoriteViewModel by lazy {
        val favoriteDao = FavoriteDatabase.getDatabase(requireContext()).favoriteDao()
        val repository = FavoriteRepository(favoriteDao)
        val viewModelFactory = ViewModelFactory2(repository)
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.GONE
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productDetailList = arguments?.getParcelable<ProductResponseItem>("PRODUCT_DATA")
        Log.d("Product Data", productDetailList.toString())

        val favProductDetailList = arguments?.getParcelable<ProductsItem>("FAV_PRODUCT_DATA")
        Log.d("Product Data", favProductDetailList.toString())

        productDetailList?.let {
            binding.apply {
                Glide.with(requireContext())
                    .load(productDetailList.productImg)
                    .fitCenter()
                    .into(productImageView)
                productName.text = productDetailList.productName
                productPrice.text = productDetailList.productPrice
                productRate.text = productDetailList.productRate
                productStock.text = productDetailList.productStock.toString()
                productDescription.text = productDetailList.productDesc
                progressBar.visibility = View.GONE
            }
        }

        favProductDetailList?.let {
            binding.apply {
                Glide.with(requireContext())
                    .load(favProductDetailList.productImg)
                    .fitCenter()
                    .into(productImageView)
                productName.text = favProductDetailList.productName
                productPrice.text = favProductDetailList.productPrice
                productRate.text = favProductDetailList.productRate
                productStock.text = favProductDetailList.productStock.toString()
                productDescription.text = favProductDetailList.productDesc
                progressBar.visibility = View.GONE
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

        binding.fabPrevious.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabShare.setOnClickListener {
            val nProductName =
                arguments?.getParcelable<ProductResponseItem>("PRODUCT_DATA")?.productName
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this product on BrainStore: $nProductName!")
                type = "text/plain"
            }

            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        productDetailList?.let {
            val productId = productDetailList.productId

            productId?.let {
                favoriteViewModel.getFavoriteProductById(it)
                    .observe(viewLifecycleOwner) { favoriteProduct ->
                        val isFavorite = favoriteProduct != null
                        binding.fabFavorite.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)

                        binding.fabFavorite.setOnClickListener {
                            if (isFavorite) {
                                favoriteViewModel.delete(favoriteProduct!!)
                                showToast("${productDetailList.productName} removed from favorites")
                            } else {
                                val favProductDetail = FavoriteProduct(
                                    id = productDetailList.productId,
                                    name = productDetailList.productName,
                                    image = productDetailList.productImg,
                                    price = productDetailList.productPrice,
                                    rating = productDetailList.productRate?.toIntOrNull()
                                )
                                favoriteViewModel.insert(favProductDetail)
                                showToast("${productDetailList.productName} added to favorites")
                            }
                        }
                    }
            }
        }

        favProductDetailList?.let {
            val productId = favProductDetailList.productId

            productId?.let {
                favoriteViewModel.getFavoriteProductById(it)
                    .observe(viewLifecycleOwner) { favoriteProduct ->
                        val isFavorite = favoriteProduct != null
                        binding.fabFavorite.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)

                        binding.fabFavorite.setOnClickListener {
                            if (isFavorite) {
                                favoriteViewModel.delete(favoriteProduct!!)
                                showToast("${favProductDetailList.productName} removed from favorites")
                            } else {
                                val favProductDetail = FavoriteProduct(
                                    id = favProductDetailList.productId,
                                    name = favProductDetailList.productName,
                                    image = favProductDetailList.productImg,
                                    price = favProductDetailList.productPrice,
                                    rating = favProductDetailList.productRate?.toIntOrNull()
                                )
                                favoriteViewModel.insert(favProductDetail)
                                showToast("${favProductDetailList.productName} added to favorites")
                            }
                        }
                    }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}