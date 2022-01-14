package com.example.schedioapp.screens.projectsOverview

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.schedioapp.R
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.databinding.FragmentProjectOverviewBinding
import com.google.android.material.chip.Chip
import kotlinx.serialization.builtins.serializer

/**
 * A [Fragment] subclass
 * Responsible for displaying all projects
 */
class ProjectOverviewFragment: Fragment() {

    lateinit var binding: FragmentProjectOverviewBinding
    lateinit var viewModel: ProjectOverviewViewModel
    lateinit var adapter: ProjectListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_overview, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        adapter = ProjectListAdapter(ProjectsListener {
            project -> view!!.findNavController().navigate(ProjectOverviewFragmentDirections.actionProjectOverviewFragmentToProjectFragment(project))
        }, ProjectsDeleteListener {
            project -> viewModel.deleteProject(project)
        }, ProjectChangeListener {
                project -> view!!.findNavController().navigate(ProjectOverviewFragmentDirections.actionProjectOverviewFragmentToProjectFragment(project))
        }
        )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}