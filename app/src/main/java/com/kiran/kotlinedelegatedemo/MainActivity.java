package com.kiran.kotlinedelegatedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kiran.kotlinedelegatedemo.adapter.MatchAdapter;
import com.kiran.kotlinedelegatedemo.delegate.ClickableItemTarget;
import com.kiran.kotlinedelegatedemo.delegate.DelegatingAdapter;
import com.kiran.kotlinedelegatedemo.delegate.ItemClickListener;
import com.kiran.kotlinedelegatedemo.domain.Match;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickListener<Match> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRV();
    }

    private void initRV() {
        List<Match> matches = new ArrayList<>();
        for(int i=0;i < 15;i++){
            Match match = new Match();
            match.setName("KIRAN: "+i);
            matches.add(match);
        }
        MatchAdapter adapter = new MatchAdapter();
        if(recyclerView != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }

        if (recyclerView.getAdapter() != adapter) {
            recyclerView.setAdapter(adapter);

            if (adapter instanceof ClickableItemTarget) {
                @SuppressWarnings("unchecked")
                ClickableItemTarget<Match> target = (ClickableItemTarget<Match>) adapter;
                target.setItemClickListener(this);
            }

            if (adapter instanceof DelegatingAdapter) {
                adapter.setup();
            }
        }

        adapter.refactorItems(matches);
    }

    @Override
    public void onClick(Match item, int position, View view) {
        Log.d("TAG","CLICKED : "+position);
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
}
