package com.example.diaryapplication.projectDetails.tabFragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryapplication.databinding.EntryItemMainBinding
import com.example.diaryapplication.databinding.EntryItemProjectBinding
import com.example.diaryapplication.model.Entry


class ProjectEntryListAdapter(private val clickListener:ProjectEntryListener) :
    ListAdapter<Entry, ProjectEntryViewHolder>(ProjectDiffCallback())
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectEntryViewHolder {
        return ProjectEntryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProjectEntryViewHolder, position: Int) {
        val entryItem = getItem(position) as Entry
        holder.itemView.setOnClickListener {
            clickListener.onClick(entryItem)
        }
        holder.bind(entryItem)
    }


}
class ProjectEntryViewHolder(private val binding: EntryItemProjectBinding) : RecyclerView.ViewHolder(
    binding.root) {



    fun bind(item: Entry){
        binding.entry = item
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent: ViewGroup) : ProjectEntryViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EntryItemProjectBinding.inflate(layoutInflater)
            return ProjectEntryViewHolder(binding)
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



interface ProjectEntryListener {
    fun onClick(entry: Entry)
}