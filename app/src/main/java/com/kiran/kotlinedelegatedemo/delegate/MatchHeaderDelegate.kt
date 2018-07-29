package com.kiran.kotlinedelegatedemo.delegate

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.kiran.kotlinedelegatedemo.R
import com.kiran.kotlinedelegatedemo.domain.Match

import butterknife.BindView

class MatchHeaderDelegate : ListAdapterDelegate<Match>(R.layout.layout_header_item, Match::class.java) {

    override fun formViewHolder(v: View): RecyclerView.ViewHolder {
        return MatchHolder(v)
    }

    protected inner class MatchHolder(view: View) : ListAdapterDelegate<Match>.AdapterViewHolder(view), ViewAdapterHolder<Match> {

        @BindView(R.id.tv_competition)
        lateinit var tvCompetition: TextView

        override fun setData(data: Match, position: Int) {
            tvCompetition!!.text = data?.name
        }

    }
}

