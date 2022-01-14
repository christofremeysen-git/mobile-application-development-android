package com.example.schedioapp.screens.projectsOverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.schedioapp.R
import com.example.schedioapp.databinding.FragmentHomeBinding

/**
 * A [Fragment] subclass
 * Not in use at the moment
 * It could be used to display projects by means of the API (for testing purposes)
 */
class ProjectOverviewApiFragment: Fragment() {
    private val projectApiViewModel: ProjectApiViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ProjectApiViewModel.Factory(activity.application)).get(ProjectApiViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.projectApiViewModel = projectApiViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}