package com.xc.brainstore.utils

import androidx.recyclerview.widget.DiffUtil
import com.xc.brainstore.data.local.entity.FavoriteProduct

class FavoriteDiffCallback(
    private val oldFavoriteProductList: List<FavoriteProduct>,
    private val newFavoriteProductList: List<FavoriteProduct>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldFavoriteProductList.size

    override fun getNewListSize(): Int = newFavoriteProductList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteProductList[oldItemPosition].id == newFavoriteProductList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteProductList[oldItemPosition] == newFavoriteProductList[newItemPosition]
    }
}