package com.kiran.kotlinedelegatedemo.delegate

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.kiran.kotlinedelegatedemo.adapter.AdapterDelegate

class AdapterDelegatesManager<T> {

    internal var delegates: SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()
    private var fallbackDelegate: AdapterDelegate<T>? = null

    fun addDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        var viewType = delegates.size()
        while (delegates.get(viewType) != null) {
            viewType++
            if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
                throw IllegalArgumentException(
                        "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate.")
            }
        }
        return addDelegate(viewType, false, delegate)
    }

    fun addDelegate(viewType: Int,
                    delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        return addDelegate(viewType, false, delegate)
    }

    fun addDelegate(viewType: Int, allowReplacingDelegate: Boolean,
                    delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {

        if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
            throw IllegalArgumentException("The view type = "
                    + FALLBACK_DELEGATE_VIEW_TYPE
                    + " is reserved for fallback adapter delegate (see setFallbackDelegate() ). Please use another view type.")
        }

        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered AdapterDelegate is "
                            + delegates.get(viewType))
        }

        delegates.put(viewType, delegate)

        return this
    }

    fun removeDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {

        val indexToRemove = delegates.indexOfValue(delegate)

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove)
        }
        return this
    }

    fun removeDelegate(viewType: Int): AdapterDelegatesManager<T> {
        delegates.remove(viewType)
        return this
    }

    fun getItemViewType(items: T, position: Int): Int {

        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate = delegates.valueAt(i)
            if (delegate.isForViewType(items, position)) {
                return delegates.keyAt(i)
            }
        }

        if (fallbackDelegate != null) {
            return FALLBACK_DELEGATE_VIEW_TYPE
        }

        throw IllegalArgumentException(
                "No AdapterDelegate added that matches position=$position in data source, for item: $items")
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var delegate: AdapterDelegate<T>? = delegates.get(viewType)
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw NullPointerException("No AdapterDelegate added for ViewType $viewType")
            } else {
                delegate = fallbackDelegate
            }
        }

        return delegate!!.onCreateViewHolder(parent)
    }

    fun onBindViewHolder(items: T, position: Int,
                         viewHolder: RecyclerView.ViewHolder) {

        getDelegateForViewType(viewHolder.itemViewType)
                .onBindViewHolder(items, position, viewHolder)
    }

    fun setFallbackDelegate(
            fallbackDelegate: AdapterDelegate<T>?): AdapterDelegatesManager<T> {
        this.fallbackDelegate = fallbackDelegate
        return this
    }

    fun getViewType(delegate: AdapterDelegate<T>): Int {

        val index = delegates.indexOfValue(delegate)
        return if (index == -1) {
            -1
        } else delegates.keyAt(index)
    }

    fun getDelegateForViewType(viewType: Int): AdapterDelegate<T> {
        var delegate: AdapterDelegate<T> = delegates.get(viewType)
        /*if (delegate == null) {
            if (fallbackDelegate == null) {
                throw NullPointerException(
                        "No AdapterDelegate added for ViewType $viewType")
            } else {
                delegate = fallbackDelegate?
            }
        }*/

        return delegate
    }

    companion object {

        internal val FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1
    }
}
