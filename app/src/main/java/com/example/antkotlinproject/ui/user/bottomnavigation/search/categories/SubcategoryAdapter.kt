package com.example.antkotlinproject.ui.user.bottomnavigation.search.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.SubcategoryModel
import com.example.antkotlinproject.databinding.ItemCategoryBinding

class SubcategoryAdapter(private val listener: SubcategoryClickListener) : BaseAdapter() {
    private var subcategoriesArray = mutableListOf<SubcategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubcategoryViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return subcategoriesArray.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = subcategoriesArray[position]
        val holder = holder as SubcategoryViewHolder
        holder.bind(item)
        holder.itemView.setOnClickListener { listener.onSubcategoryClick(item) }
    }

    fun addItems(item: MutableList<SubcategoryModel>) {
        subcategoriesArray = item
        notifyDataSetChanged()
    }
}

interface SubcategoryClickListener {
    fun onSubcategoryClick(item: SubcategoryModel)
}

class SubcategoryViewHolder(var binding: ItemCategoryBinding) : BaseViewHolder(binding.root) {
    fun bind(item: SubcategoryModel) {
        binding.title.text = item.name
    }
}