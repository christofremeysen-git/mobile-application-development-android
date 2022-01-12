package com.example.schedioapp.screens.projectsOverview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.schedioapp.R
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.databinding.FragmentProjectOverviewBinding
import com.google.android.material.chip.Chip
import timber.log.Timber
import java.util.*
import kotlin.streams.toList

class ProjectOverviewFragment: Fragment() {

    lateinit var binding: FragmentProjectOverviewBinding
    lateinit var viewModel: ProjectOverviewViewModel
    lateinit var adapter: ProjectListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_overview, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        adapter = ProjectListAdapter(ProjectsListener {
            // naam -> Toast.makeText(context, "${naam}", Toast.LENGTH_SHORT).show()
            naam -> view!!.findNavController().navigate(ProjectOverviewFragmentDirections.actionProjectOverviewFragmentToAboutFragment())

        }, ProjectsDeleteListener {
            // project -> Toast.makeText(context, "${project.id}", Toast.LENGTH_SHORT).show()
            project -> viewModel.deleteProject(project)
        })
        binding.projectList.adapter = adapter

        val viewModelFactory = ProjectOverviewViewModelFactory(dataSource, application, adapter)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectOverviewViewModel::class.java)

        binding.projectOverviewViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.projects.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        createChips(listOf("Bezig", "Hangende", "Open", "Voltooid"))
        return binding.root
    }

    private fun createChips(data: List<String>) {
        val chipGroup = binding.chipList
        val inflater = LayoutInflater.from(chipGroup.context)

        val children = data.map {
            text ->
            val chip = inflater.inflate(R.layout.chip, chipGroup, false) as Chip
            chip.text = text
            chip.tag = text
            chip.setOnCheckedChangeListener {
                button, isChecked ->
                viewModel.filterChip(button.tag as String, isChecked)
            }
            chip
        }

        chipGroup.removeAllViews()

        for(chip in children) {
            chipGroup.addView(chip)
        }
    }

}