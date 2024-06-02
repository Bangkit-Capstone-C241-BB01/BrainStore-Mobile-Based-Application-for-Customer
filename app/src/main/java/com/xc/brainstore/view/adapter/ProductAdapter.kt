package com.xc.brainstore.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xc.brainstore.databinding.ItemProductBinding

//class ProductAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
//
//    private var listener: OnItemClickListener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemProductBinding.inflate(inflater, parent, false)
//        return ProductViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = getItem(position)
//        if (holder is ProductViewHolder) {
//            when (item) {
//                is ProductResponse -> holder.bindProduct(item)
//                is ItemsItem -> {
//                    holder.bindProductItem(item)
//                    holder.itemView.setOnClickListener {
//                        listener?.onItemClick(item)
//                    }
//                }
//            }
//        }
//    }
//
//    inner class ProductViewHolder(private val binding: ItemProductBinding) :
//        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
//        init {
//            binding.root.setOnClickListener(this)
//        }
//
//        fun bindProduct(product: ProductResponse) {
//            binding.apply {
//                Glide.with(binding.productImageView)
//                    .load(product.image)
//                    .into(binding.productImageView)
//
//                productName.text = product.name
//                productPrice.text = product.price
//                productRate.text = product.rate
//            }
//        }
//
//        fun bindProductItem(productItem: ItemsItem) {
//            Glide.with(binding.productImageView)
//                .load(productItem.image)
//                .into(binding.productImageView)
//
//            binding.productName.text = productItem.name
//            binding.productPrice.text = productItem.price
//            binding.productRate.text = productItem.rate
//        }
//
//        override fun onClick(v: View?) {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                val product = getItem(position)
//                listener?.onItemClick(product)
//            }
//        }
//    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(product: Any)
//    }
//
//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
//            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
//                return when {
//                    oldItem is ProductResponse && newItem is ProductResponse -> oldItem.id == newItem.id
//                    oldItem is ItemsItem && newItem is ItemsItem -> oldItem.id == newItem.id
//                    else -> false
//                }
//            }
//
//            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
//                return false
//            }
//        }
//    }
//}