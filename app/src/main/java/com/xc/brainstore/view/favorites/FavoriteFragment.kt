package com.xc.brainstore.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xc.brainstore.R
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.local.room.FavoriteDatabase
import com.xc.brainstore.data.repository.FavoriteRepository
import com.xc.brainstore.databinding.FragmentFavoriteBinding
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.ProductViewModelFactory
import com.xc.brainstore.view.ViewModelFactory2
import com.xc.brainstore.view.adapter.FavoriteAdapter
import com.xc.brainstore.view.detail.DetailProductViewModel

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
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
        favoriteViewModel = ViewModelProvider(this,
            ViewModelFactory2(favoriteRepository)
        )[FavoriteViewModel::class.java]

        favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClick(product: FavoriteProduct) {
                detailViewModel.productDetail.observe(viewLifecycleOwner) { favProductDetail ->
                    val bundle = Bundle().apply {
                        putParcelable("FAV_PRODUCT_DATA", favProductDetail)
                    }

                    findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
                }
            }
        })

        binding.rvFavoriteProduct.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { favoriteProduct ->
            favoriteAdapter.submitList(favoriteProduct)
            binding.message.visibility =
                if (favoriteProduct.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(product: FavoriteProduct) {
        detailViewModel.getProductDetail(product.id)
    }
}