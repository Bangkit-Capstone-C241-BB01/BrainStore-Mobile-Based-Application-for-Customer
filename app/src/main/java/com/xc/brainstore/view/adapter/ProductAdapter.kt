package com.xc.brainstore.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.ProductsItem
import com.xc.brainstore.databinding.ItemProductBinding
import com.xc.brainstore.utils.formatRupiah

class ProductAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is ProductViewHolder) {
            when (item) {
                is ProductResponseItem -> holder.bindProduct(item)
                is ProductsItem -> {
                    holder.bindProductItem(item)
                    holder.itemView.setOnClickListener {
                        listener?.onItemClick(item)
                    }
                }
            }
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindProduct(product: ProductResponseItem) {
            val price = product.productPrice.toString()
            val formattedPrice = formatRupiah(price)

            binding.apply {
                Glide.with(binding.productImageView)
                    .load(product.productImg)
                    .into(binding.productImageView)

                productName.text = product.productName
                productPrice.text = formattedPrice
                productRate.text = product.productRate
            }
        }

        fun bindProductItem(productItem: ProductsItem) {
            val price = productItem.productPrice.toString()
            val formattedPrice = formatRupiah(price)

            binding.apply {
                Glide.with(productImageView)
                    .load(productItem.productImg)
                    .into(productImageView)

                productName.text = productItem.productName
                productPrice.text = formattedPrice
                productRate.text = productItem.productRate
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val product = getItem(position)
                listener?.onItemClick(product)
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(product: Any)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return when {
                    oldItem is ProductResponseItem && newItem is ProductResponseItem -> oldItem.productId == newItem.productId
                    oldItem is ProductsItem && newItem is ProductsItem -> oldItem.productId == newItem.productId
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return false
            }
        }
    }
}