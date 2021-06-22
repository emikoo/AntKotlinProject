package com.example.antkotlinproject.ui.user.bottomnavigation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.databinding.ItemCategoryBinding

class CategoryAdapter : BaseAdapter() {
    private var categoriesArray = mutableListOf<CategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoriesArray.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = categoriesArray[position]
        val holder = holder as CategoryViewHolder
        holder.bind(item)
    }

    fun addItems(item: MutableList<CategoryModel>) {
        categoriesArray = item
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(var binding: ItemCategoryBinding) : BaseViewHolder(binding.root) {
    fun bind(item: CategoryModel) {
        binding.title.text = item.name
    }
}