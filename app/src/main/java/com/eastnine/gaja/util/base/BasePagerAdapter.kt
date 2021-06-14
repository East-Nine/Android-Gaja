package com.eastnine.gaja.util.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagerAdapter<VB: ViewDataBinding, ITEM : Any>(
    private val context: Context,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : PagingDataAdapter<ITEM, BasePagerAdapter.BaseViewHolder<VB>>(diffUtil) {

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun onViewHolder(binding: VB, position: Int, data: ITEM)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<VB>(inflater, getLayoutId(), parent, false)

        return BaseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        getItem(position)?.let { item ->
            onViewHolder(holder.binding, position, item)
        }
    }

    class BaseViewHolder<VB: ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}