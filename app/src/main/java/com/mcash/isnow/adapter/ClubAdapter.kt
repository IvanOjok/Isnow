package com.mcash.isnow.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mcash.isnow.R
import com.mcash.isnow.model.AccountData
import com.mcash.isnow.ui.auth.Verification

class ClubAdapter(context: Context, l: ArrayList<AccountData>): RecyclerView.Adapter<ClubAdapter.ClubHolder>() {
    private val con = context
    private val list = l
    inner class ClubHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textView: TextView = itemView.findViewById<TextView>(R.id.club_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubHolder {
        val layoutInflater = LayoutInflater.from(con).inflate(R.layout.layout_groups, parent, false)
        return ClubHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ClubHolder, position: Int) {
        holder.textView.text = list[position].name
        holder.textView.setOnClickListener {
            val intent = Intent(con, Verification::class.java)
            intent.putExtra("id", list[position].id.toString())
            Log.d("IC", list[position].id.toString())
            con.startActivity(intent)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}