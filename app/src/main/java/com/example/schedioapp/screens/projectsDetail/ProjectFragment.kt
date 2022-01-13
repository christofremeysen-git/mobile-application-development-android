package com.example.schedioapp.screens.projectsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.schedioapp.R
import com.example.schedioapp.databinding.FragmentProjectBinding
import com.example.schedioapp.screens.addProject.AddProjectViewModel

class ProjectFragment: Fragment() {

    private val args: ProjectFragmentArgs by navArgs()

    private lateinit var binding: FragmentProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ProjectViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectViewModel::class.java)

        binding.detailProjectNaam.text = args.project.naam
        binding.detailProjectStartdatum.text = args.project.startDatum.toString()
        binding.detailProjectEinddatum.text = args.project.eindDatum.toString()
        binding.detailProjectBudget.text = args.project.budget.toString()
        binding.detailProjectStatus.text = args.project.status
        binding.detailProjectType.text = args.project.type
        binding.projectViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}
