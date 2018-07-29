package com.kiran.kotlinedelegatedemo.delegate

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kiran.kotlinedelegatedemo.adapter.AdapterDelegate
import com.kiran.kotlinedelegatedemo.domain.ListItem

import butterknife.ButterKnife

abstract class ListAdapterDelegate<T : ListItem> protected constructor(private val layoutId: Int, private val recordType: Class<T>?) : AdapterDelegate<MutableList<T>> {

    private var delegateClickListener: DelegateClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val minInflater = LayoutInflater.from(parent.context)
        val view = minInflater.inflate(layoutId, parent, false)
        return formViewHolder(view)
    }

    override fun isForViewType(items: MutableList<T>, position: Int): Boolean {
        val record = items[position]
        return recordType?.isInstance(record) ?: (record == null)
    }

    override fun onBindViewHolder(items: MutableList<T>, position: Int, holder: RecyclerView.ViewHolder) {
        val viewModel = items[position]
        (holder as ViewAdapterHolder<T>).setData(viewModel, position)
    }

    protected abstract fun formViewHolder(v: View): RecyclerView.ViewHolder

    override fun setDelegateClickListener(delegateClickListener: DelegateClickListener) {
        this.delegateClickListener = delegateClickListener
    }

    open inner class AdapterViewHolder protected constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            ButterKnife.bind(this, view)
            Log.d("TAG","-----------------: "+view)
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (delegateClickListener != null) {
                delegateClickListener!!.onClick(layoutPosition, view)
            }
        }
    }
}

