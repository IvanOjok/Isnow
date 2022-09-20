package com.mcash.isnow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mcash.isnow.R
import com.mcash.isnow.model.ContributionEntity

class HistoryAdapter(context: Context, list: List<ContributionEntity>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val contributions = list
    val c = context
    class HistoryViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val type = itemView.findViewById<TextView>(R.id.reason)
        val amount = itemView.findViewById<TextView>(R.id.amount)
        val date = itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(c).inflate(R.layout.layout_history_view, parent, false)
        return HistoryViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val contributionId = contributions[position].contribution_id
        if(contributionId == "1"){
            holder.type.text =  "Monthly Contribution"
        }
        else if (contributionId == "2"){
            holder.type.text =  "Club Expenses"
        }
        else if (contributionId == "3"){
            holder.type.text =  "Club Joining Fee"
        }

        holder.amount.text = "UGX. ${contributions[position].contribution_amount.toString()}"
        holder.date.text = contributions[position].created_at
    }

    override fun getItemCount(): Int {
        return contributions.size
    }
}