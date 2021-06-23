package com.example.diaryapplication.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryapplication.databinding.EntryItemMainBinding
import com.example.diaryapplication.model.Entry


class EntryListAdapter(private val clickListener:EntryListener) : ListAdapter<Entry, EntryViewHolder>(ProjectDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entryItem = getItem(position) as Entry

        holder.itemView.setOnClickListener {
            clickListener.onClick(entryItem)
        }
        holder.bind(entryItem)
    }


}
class EntryViewHolder(private val binding: EntryItemMainBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(item: Entry){
        binding.entry = item
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent: ViewGroup) : EntryViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EntryItemMainBinding.inflate(layoutInflater)
            return EntryViewHolder(binding)
        }
    }

}

class ProjectDiffCallback : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem == newItem
    }
}

interface EntryListener {

    fun onClick(entry: Entry)

}