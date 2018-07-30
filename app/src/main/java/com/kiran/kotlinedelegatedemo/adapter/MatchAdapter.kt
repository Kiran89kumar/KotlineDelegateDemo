package com.kiran.kotlinedelegatedemo.adapter

import com.kiran.kotlinedelegatedemo.delegate.DelegatingListAdapter
import com.kiran.kotlinedelegatedemo.delegate.ListAdapterDelegate
import com.kiran.kotlinedelegatedemo.delegate.MatchHeaderDelegate
import com.kiran.kotlinedelegatedemo.domain.ListItem

class MatchAdapter<E : ListItem> : DelegatingListAdapter<E>() {

    override fun createDelegates(): Array<AdapterDelegate<MutableList<E>>> {
        val delegate: ListAdapterDelegate<E> = clickable(MatchHeaderDelegate())
                as ListAdapterDelegate<E>;
        return arrayOf(delegate)
    }
}
