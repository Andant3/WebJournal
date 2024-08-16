package com.example.webjournal.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.webjournal.R
import com.example.webjournal.databinding.PageItemBinding
import com.example.webjournal.model.Journal

class JournalRecyclerAdapter(val context: Context, var journalList: List<Journal>): RecyclerView.Adapter<JournalRecyclerAdapter.JournalViewHolder>() {


    private lateinit var binding: PageItemBinding

    class JournalViewHolder(itemView: View, context: Context): ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.page_item, parent, false)
        return JournalViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {

        val journal: Journal = journalList.get(position)
        var imageURL: String = ""
    }
}