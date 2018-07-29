package com.kiran.kotlinedelegatedemo.delegate

import android.view.View

interface ItemClickListener<T> {
    fun onClick(item: T, position: Int, view: View)
}

