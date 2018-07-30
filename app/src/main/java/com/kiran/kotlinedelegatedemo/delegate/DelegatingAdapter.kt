package com.kiran.kotlinedelegatedemo.delegate

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.kiran.kotlinedelegatedemo.adapter.AdapterDelegate
import com.kiran.kotlinedelegatedemo.domain.ListItem

abstract class DelegatingAdapter<T> internal constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>(), DelegateClickListener {

    open var data: T? = null
    open var filteredData: T? = null
    private val adm: AdapterDelegatesManager<T>

    init {
        this.adm = AdapterDelegatesManager<T>()
    }

    fun setup() {
        val delegates = createDelegates()
        val size: Int? = delegates?.size
        if (size!=null && size > 0) {
            for (i in 0 until size) {
                adm.addDelegate(delegates[i])
            }
        }
    }

    protected abstract fun createDelegates(): Array<AdapterDelegate<T>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adm.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adm.onBindViewHolder(filteredData!!, position, holder)
    }

    override fun onClick(position: Int, v: View) {

    }

    override fun getItemViewType(position: Int): Int {
        return adm.getItemViewType(filteredData!!, position)
    }

    protected fun clickable(delegate: ListAdapterDelegate<*>): ListAdapterDelegate<*> {
        delegate.setDelegateClickListener(this)
        return delegate
    }
}

