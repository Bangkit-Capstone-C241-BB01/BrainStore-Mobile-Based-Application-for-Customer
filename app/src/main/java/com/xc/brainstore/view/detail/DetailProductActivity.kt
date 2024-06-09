package com.xc.brainstore.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.xc.brainstore.R
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.local.room.FavoriteDatabase
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.SellerResponse
import com.xc.brainstore.data.repository.FavoriteRepository
import com.xc.brainstore.databinding.ActivityDetailProductBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.utils.formatRupiah
import com.xc.brainstore.view.ProductViewModelFactory
import com.xc.brainstore.view.ViewModelFactory2
import com.xc.brainstore.view.favorites.FavoriteViewModel

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val repository by lazy { Injection.provideProductRepository(this) }

    private val detailViewModel: DetailProductViewModel by lazy {
        val factory = ProductViewModelFactory(repository)
        ViewModelProvider(this, factory)[DetailProductViewModel::class.java]
    }

    private val favoriteViewModel: FavoriteViewModel by lazy {
        val favoriteDao = FavoriteDatabase.getDatabase(this).favoriteDao()
        val repository = FavoriteRepository(favoriteDao)
        val viewModelFactory = ViewModelFactory2(repository)
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        Log.d("DetailProductActivity", "onCreate called")
        setContentView(binding.root)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("DetailProductActivity", "onNewIntent called")
        handleIntent(intent)
    }

    @Suppress("DEPRECATION")
    private fun handleIntent(intent: Intent) {
        val productDetailList = intent.getParcelableExtra<ProductResponseItem>("PRODUCT_DATA")
        val storeDetail = intent.getParcelableExtra<SellerResponse>("STORE_DATA")

        productDetailList?.let {
            val price = productDetailList.productPrice.toString()
            val formattedPrice = formatRupiah(price)

            storeDetail?.let {
                binding.apply {
                    Glide.with(this@DetailProductActivity)
                        .load(productDetailList.productImg)
                        .fitCenter()
                        .into(productImageView)
                    productName.text = productDetailList.productName
                    productPrice.text = formattedPrice
                    productRate.text = productDetailList.productRate
                    productStock.text = productDetailList.productStock.toString()
                    productSpecification.text = productDetailList.productSpec
                    productDescription.text = productDetailList.productDesc
                    Glide.with(this@DetailProductActivity)
                        .load(storeDetail.storeImg)
                        .fitCenter()
                        .into(storeProfile)
                    storeName.text = storeDetail.storeName
                    storeLocation.text = storeDetail.storeLocation
                }
            }
        }
        
        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        detailViewModel.message.observe(this) { message ->
            message?.let {
                showToast(it)
            }
        }

        binding.fabPrevious.setOnClickListener {
            finish()
        }

        binding.fabShare.setOnClickListener {
            val nProductName = productDetailList?.productName
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
                    .observe(this) { favoriteProduct ->
                        val isFavorite = favoriteProduct != null
                        binding.fabFavorite.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
                        binding.fabFavorite.setOnClickListener {
                            if (isFavorite) {
                                favoriteViewModel.delete(favoriteProduct!!)
                                showToast("${productDetailList.productName} removed from favorites")
                            } else {
                                val favProductDetail = FavoriteProduct(
                                    storeId = productDetailList.storeId,
                                    id = productDetailList.productId,
                                    productImg = productDetailList.productImg,
                                    productName = productDetailList.productName,
                                    productPrice = productDetailList.productPrice,
                                    productRate = productDetailList.productRate,
                                    productStock = productDetailList.productStock,
                                    productSpec = productDetailList.productSpec,
                                    productDesc = productDetailList.productDesc
                                )
                                favoriteViewModel.insert(favProductDetail)
                                showToast("${productDetailList.productName} added to favorites")
                            }
                        }
                    }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}