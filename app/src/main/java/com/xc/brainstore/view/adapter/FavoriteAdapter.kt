package com.xc.brainstore.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.databinding.ItemFavoriteBinding
import com.xc.brainstore.utils.FavoriteDiffCallback
import com.xc.brainstore.utils.formatRupiah

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteProductViewHolder>() {

    private var favoriteList: List<FavoriteProduct> = emptyList()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    inner class FavoriteProductViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(favoriteProduct: FavoriteProduct) {
            val price = favoriteProduct.price.toString()
            val formattedPrice = formatRupiah(price)

            binding.apply {
                Glide.with(productImageView)
                    .load(favoriteProduct.image)
                    .into(productImageView)

                productName.text = favoriteProduct.name
                productPrice.text = formattedPrice
                productRate.text = favoriteProduct.rating.toString()
            }
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val favoriteProduct = favoriteList[position]
                listener?.onItemClick(favoriteProduct)
            }
        }
    }

    fun submitList(newList: List<FavoriteProduct>) {
        val diffResult = DiffUtil.calculateDiff(FavoriteDiffCallback(favoriteList, newList))
        favoriteList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(product: FavoriteProduct)
    }
}