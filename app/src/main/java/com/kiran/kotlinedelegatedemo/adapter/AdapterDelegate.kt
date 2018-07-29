package com.kiran.kotlinedelegatedemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.kiran.kotlinedelegatedemo.delegate.DelegateClickListener

interface AdapterDelegate<T> {

    fun isForViewType(items: T, position: Int): Boolean

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(items: T, position: Int, holder: RecyclerView.ViewHolder)

    fun setDelegateClickListener(delegateClickListener: DelegateClickListener)
}

