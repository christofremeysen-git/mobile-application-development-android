package com.example.schedioapp.screens.addProject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getBinding
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.schedioapp.R
import com.example.schedioapp.database.project.DateConverter
import com.example.schedioapp.database.project.ProjectDatabase
import com.example.schedioapp.databinding.FragmentAddProjectBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class MaterialDateConverter {
    fun toDate(date: String): Date {
        var result: Date = dateTimeDateConversion(date)
        return date?.let { result }
    }

    fun dateTimeDateConversion(date: String): Date {
        var year: Int = date.split(" ")[2].toInt()
        var month: Int = 0
        var monthString: String = date.split(" ")[0]
        when(monthString) {
            "Jan" -> month = 1
            "Feb" -> month = 2
            "Mar" -> month = 3
            "Apr" -> month= 4
            "May" -> month = 5
            "Jun" -> month = 6
            "Jul" -> month = 7
            "Aug" -> month = 8
            "Sep" -> month = 9
            "Oct" -> month = 10
            "Nov" -> month = 11
            "Dec" -> month = 12
        }

        var day: Int = date.split(" ")[1].split(",")[0].toInt()

        val cal: Calendar = GregorianCalendar(year, month, day)

        val date: Date = cal.time
        return date
    }

}

class AddProjectFragment: Fragment() {

    lateinit var binding: FragmentAddProjectBinding
    lateinit var viewModel: AddProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_project, container, false)

        val datePickerStartdatum =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        val datePickerEinddatum =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        binding.projectStartdatumCalendar.setOnClickListener {
            datePickerStartdatum.show(parentFragmentManager,"projectStartdatumCalendar")
        }

        binding.projectEinddatumCalendar.setOnClickListener {
            datePickerEinddatum.show(parentFragmentManager,"projectEinddatumCalendar")
        }

        datePickerStartdatum.addOnPositiveButtonClickListener {
            binding.projectStartdatum.setText(datePickerStartdatum.headerText)
        }

        datePickerEinddatum.addOnPositiveButtonClickListener {
            binding.projectEinddatum.setText(datePickerEinddatum.headerText)
        }

        val app = requireNotNull(this.activity).application
        val dataSource = ProjectDatabase.getInstance(app).projectDatabaseDao

        val viewModelFactory = AddProjectViewModelFactory(dataSource, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddProjectViewModel::class.java)

        binding.addProjectViewModel = viewModel

        binding.setLifecycleOwner(this)

        viewModel.projectSubmitEvent.observe(viewLifecycleOwner, Observer {
            projectSubmitEvent -> if(projectSubmitEvent) {
                viewModel.submitProject(
                    binding.projectNaam.text.toString(),
                    MaterialDateConverter().toDate(binding.projectStartdatum.text.toString()),
                    MaterialDateConverter().toDate(binding.projectEinddatum.text.toString()),
                    binding.projectBudget.text.toString().toDouble(),
                    binding.projectStatus.text.toString(),
                    binding.projectType.text.toString()
                )

                view?.findNavController()?.navigate(AddProjectFragmentDirections.actionAddProjectFragmentToProjectOverviewFragment())
                viewModel.submitEventDone()
            }
        })

        return binding.root
    }

}