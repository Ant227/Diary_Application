package com.example.diaryapplication.ProjectsList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryapplication.databinding.ProjectItemBinding
import com.example.diaryapplication.model.Project

class ProjectListAdapter(private val clickListener:ProjectListener) : ListAdapter<Project, ProjectViewHolder>(ProjectDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
       return ProjectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val projectItem = getItem(position) as Project

        holder.itemView.setOnClickListener {
            clickListener.onClick(projectItem)
        }
        holder.bind(projectItem)
    }


}
class ProjectViewHolder(private val binding: ProjectItemBinding) : RecyclerView.ViewHolder(
    binding.root) {
    fun bind(item: Project){
        binding.project = item
        item.color?.let {
            binding.projectCircleView.circleColor = item.color
        }

        binding.executePendingBindings()
    }

    companion object{
        fun from(parent:ViewGroup) : ProjectViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProjectItemBinding.inflate(layoutInflater)
            return ProjectViewHolder(binding)
        }
    }

}

class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {


    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}

interface ProjectListener {

    fun onClick(project: Project)

}