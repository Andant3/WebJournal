package com.example.webjournal.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.webjournal.databinding.PageItemBinding
import com.example.webjournal.model.Journal

class JournalRecyclerAdapter(private val context: Context, private var journalList: List<Journal>): RecyclerView.Adapter<JournalRecyclerAdapter.JournalViewHolder>() {


    private lateinit var binding: PageItemBinding

    class JournalViewHolder(var binding: PageItemBinding)
        : ViewHolder(binding.root){

            fun bind(journal: Journal){
                binding.journal = journal
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        //val view = LayoutInflater.from(context).inflate(R.layout.page_item, parent, false)

        binding = PageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JournalViewHolder(binding)
    }

    override fun getItemCount(): Int = journalList.size

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val journal: Journal = journalList.get(position)
        holder.bind(journal)
    }
}