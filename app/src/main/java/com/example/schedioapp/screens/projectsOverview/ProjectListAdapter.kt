package com.example.schedioapp.screens.projectsOverview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.schedioapp.databinding.ProjectListItemBinding
import com.example.schedioapp.domain.Project
import com.example.schedioapp.screens.projectsOverview.ProjectListAdapter.ProjectListViewHolder
import retrofit2.http.Tag

/**
 * A [ListAdapter] implementation
 *
 * @property clickListener Used to listen for clicks on a project
 * @property clickDeleteListener Used to listen for clicks on a delete project icon
 * @property projectChangeListener Used to listen for clicks on the project edit icon
 */
class ProjectListAdapter(val clickListener: ProjectsListener, val clickDeleteListener: ProjectsDeleteListener, val projectChangeListener: ProjectChangeListener): ListAdapter<Project, ProjectListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ProjectListViewHolder(private var binding: ProjectListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ProjectsListener, clickDeleteListener: ProjectsDeleteListener, projectChangeListener: ProjectChangeListener, item: Project) {
            binding.projectNaam.text = item.naam
            binding.project = item
            binding.clickListener = clickListener
            binding.clickDeleteListener = clickDeleteListener
            binding.projectChangeListener = projectChangeListener
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
        holder.bind(clickListener, clickDeleteListener, projectChangeListener, item)
    }

}

class ProjectsListener(val clickListener: (project: Project) -> Unit) {
    fun onClick(project: Project) = clickListener(project)
}

class ProjectsDeleteListener(val clickDeleteListener: (project: Project) -> Unit) {
    fun onClick(project: Project) = clickDeleteListener(project)
}

class ProjectChangeListener(val projectChangeListener: (project: Project) -> Unit) {
    fun onClick(project: Project) = projectChangeListener(project)
}