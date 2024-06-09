package com.xc.brainstore.view.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xc.brainstore.R
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.local.room.FavoriteDatabase
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.SellerResponse
import com.xc.brainstore.data.repository.FavoriteRepository
import com.xc.brainstore.databinding.FragmentFavoriteBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ProductViewModelFactory
import com.xc.brainstore.view.ViewModelFactory2
import com.xc.brainstore.view.adapter.FavoriteAdapter
import com.xc.brainstore.view.detail.DetailProductActivity
import com.xc.brainstore.view.detail.DetailProductViewModel

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private var productDetail: ProductResponseItem? = null
    private var storeDetail: SellerResponse? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val repository by lazy { Injection.provideProductRepository(requireActivity()) }

    private val detailViewModel: DetailProductViewModel by lazy {
        val factory = ProductViewModelFactory(repository)
        ViewModelProvider(this, factory)[DetailProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.GONE
        binding.message.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        val favoriteRepository = FavoriteRepository(favoriteDao)
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelFactory2(favoriteRepository)
        )[FavoriteViewModel::class.java]

        favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setOnItemClickListener(this)

        binding.rvFavoriteProduct.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { favoriteProduct ->
            favoriteAdapter.submitList(favoriteProduct)
            binding.message.visibility =
                if (favoriteProduct.isEmpty()) View.VISIBLE else View.GONE
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fav_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_trash -> {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle(context.getString(R.string.delete_confirmation))
                            setMessage(context.getString(R.string.delete_confirmation_question))
                            setPositiveButton(context.getString(R.string.yes_act)) { _, _ ->
                                favoriteViewModel.deleteAll()
                            }
                            setNegativeButton(context.getString(R.string.no_act)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            show()
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.progressBar.visibility = View.GONE
    }

    override fun onItemClick(product: FavoriteProduct) {
        detailViewModel.getProductDetail(product.id)
        detailViewModel.getStoreDetail(product.storeId)

        detailViewModel.productDetail.observe(viewLifecycleOwner) { productDetail ->
            this.productDetail = productDetail
            detailViewModel.storeDetail.observe(viewLifecycleOwner) { storeDetail ->
                this.storeDetail = storeDetail
                if (productDetail != null && storeDetail != null) {
                    navigateToDetailActivity()
                }
            }
        }
    }

    private fun navigateToDetailActivity() {
        val intent = Intent(requireContext(), DetailProductActivity::class.java).apply {
            putExtra("PRODUCT_DATA", productDetail)
            putExtra("STORE_DATA", storeDetail)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}