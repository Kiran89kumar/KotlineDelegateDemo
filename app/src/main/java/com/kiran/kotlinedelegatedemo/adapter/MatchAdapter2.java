package com.kiran.kotlinedelegatedemo.adapter;

import com.kiran.kotlinedelegatedemo.delegate.DelegatingListAdapter;
import com.kiran.kotlinedelegatedemo.delegate.ListAdapterDelegate;
import com.kiran.kotlinedelegatedemo.delegate.MatchHeaderDelegate;
import com.kiran.kotlinedelegatedemo.domain.ListItem;

public class MatchAdapter2<T extends ListItem> extends DelegatingListAdapter<T> {

    @Override
    protected AdapterDelegate[] createDelegates() {

        clickable(new MatchHeaderDelegate());

        return new ListAdapterDelegate[] {
                clickable(new MatchHeaderDelegate())
        };
    }
}