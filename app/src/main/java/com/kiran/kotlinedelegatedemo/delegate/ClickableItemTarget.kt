package com.kiran.kotlinedelegatedemo.delegate

interface ClickableItemTarget<T> {

    fun setItemClickListener(listener: ItemClickListener<T>)
}

