package com.eastnine.gaja.util.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VB: ViewDataBinding, ITEM : Any>(
    private val context: Context,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : ListAdapter<ITEM, BaseAdapter.BaseViewHolder<VB>>(diffUtil) {
    private val _dataArray = MutableLiveData<MutableList<ITEM>>()
    val dataArray: LiveData<MutableList<ITEM>> = _dataArray

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun onViewHolder(binding: VB, position: Int, data: ITEM)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<VB>(inflater, getLayoutId(), parent, false)

        return BaseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        getData(position)?.let { item ->
            onViewHolder(holder.binding, position, item)
        }
    }

    fun addData(data: ITEM) {
        val newDataList = dataArray.value ?: mutableListOf()
        newDataList.add(data)
        _dataArray.value = newDataList
        setDiffList()
    }
    
    fun addAllData(data: List<ITEM>) {
        val newDataList = dataArray.value ?: mutableListOf()
        newDataList.addAll(data)
        _dataArray.value = newDataList
        setDiffList()
    }

    private fun getItemSize(): Int = dataArray.value?.size ?: 0

    protected fun getData(position: Int): ITEM? =
        if (position >= 0 && getItemSize() > position) {
            dataArray.value?.get(position)
        } else {
            null
        }
    
    fun setData(data: ITEM) {
        _dataArray.value?.indexOf(data)?.let { position ->
            val newDataList = dataArray.value ?: mutableListOf()
            newDataList[position] = data
            _dataArray.value = newDataList
            setDiffList()
        }
    }

    fun replaceData(data: ITEM) {
        dataArray.value?.let { list ->
            val index = list.indexOf(data)
            list[index] = data
            _dataArray.value = list.toMutableList()
            setDiffList()
        }
    }

    fun replaceAllData(data: List<ITEM>) {
        _dataArray.value = data.toMutableList()
        setDiffList()
    }
    
    fun removeData(data: ITEM) {
        val newDataList = dataArray.value ?: mutableListOf()
        newDataList.remove(data)
        _dataArray.value = newDataList

        setDiffList()
    }
    
    fun removeAllData(data: List<ITEM>) {
        val newDataList = dataArray.value ?: mutableListOf()
        newDataList.removeAll(data)
        _dataArray.value = newDataList

        setDiffList()
    }
    
    fun clear() {
        _dataArray.value = mutableListOf()
        setDiffList()
    }

    private fun setDiffList() {
        submitList(dataArray.value)
    }

    class BaseViewHolder<VB: ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}