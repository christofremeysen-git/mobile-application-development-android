package com.example.schedioapp.screens.projectsOverview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.schedioapp.databinding.ProjectListItemBinding
import com.example.schedioapp.domain.Project
import com.example.schedioapp.screens.projectsOverview.ProjectListAdapter.ProjectListViewHolder

class ProjectListAdapter(val clickListener: ProjectsListener): ListAdapter<Project, ProjectListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ProjectListViewHolder(private var binding: ProjectListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ProjectsListener, item: Project) {
            binding.projectNaam.text = item.naam
            binding.project = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProjectListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProjectListItemBinding.inflate(layoutInflater, parent, false)
                return ProjectListViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        return ProjectListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

}

class ProjectsListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(project: Project) = clickListener(project.id)
}