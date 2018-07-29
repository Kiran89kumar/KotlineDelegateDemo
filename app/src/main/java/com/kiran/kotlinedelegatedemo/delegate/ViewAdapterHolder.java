package com.kiran.kotlinedelegatedemo.delegate;

public interface ViewAdapterHolder<T> {
    void setData(T data, int position);
}
