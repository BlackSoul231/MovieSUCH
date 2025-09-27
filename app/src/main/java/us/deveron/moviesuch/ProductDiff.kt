package us.deveron.moviesuch

import androidx.recyclerview.widget.DiffUtil

class ProductDiff(val oldList: ArrayList<Product>, val newList: ArrayList<Product>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return oldProduct.name == newProduct.name && oldProduct.desc == newProduct.desc && oldProduct.idIcon == newProduct.idIcon
    }
}