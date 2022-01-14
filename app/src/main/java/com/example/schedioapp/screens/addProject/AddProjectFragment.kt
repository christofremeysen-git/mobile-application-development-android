package com.example.schedioapp.screens.addProject

import android.os.Bundle
import android.os.Parcel
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class MaterialDateConverter {
    fun toDate(date: String): Date {
        var result: Date = dateTimeDateConversion(date)
        return result
    }

    fun toLong(date: String): Long {
        var result: Long = dateTimeLongConversion(date)
        return result
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

    fun dateTimeLongConversion(date: String): Long {
        var datum = dateTimeDateConversion(date)
        var cal = GregorianCalendar()
        cal.time = datum

        var result = cal.timeInMillis

        return result
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

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePickerStartdatum =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecteer datum")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        val datePickerEinddatum =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecteer datum")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
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

        var projectNaam = binding.projectNaam.text
        var projectBudget = binding.projectBudget.text
        var projectStatus = binding.projectStatus.text
        var projectType = binding.projectType.text

        fun projectNaamValidator(projectNaam: Editable) {
            var projectNaam = projectNaam.toString()

            if(projectNaam.length < 5) {
                binding.projectNaam.error = getResources().getString(R.string.too_short_project_name)
            }

            if(projectNaam.length > 25) {
                binding.projectNaam.error = getResources().getString(R.string.too_long_project_name)
            }
        }

        fun projectDatesValidator(projectStartDatum: Editable, projectEindDatum: Editable): Boolean {
            var status = false
            var dateConverterStart = MaterialDateConverter()
            var dateConverterEnd = MaterialDateConverter()
            val projectStartDate = projectStartDatum.toString()
            var projectEndDate = projectEindDatum.toString()
            var projectLongStart = 0L
            var projectLongEnd = 0L

            if(!projectStartDate.isBlank()) {
                projectLongStart = dateConverterStart.toLong(projectStartDate)
            }
            if(!projectEndDate.isBlank()) {
                projectLongEnd = dateConverterEnd.toLong(projectEndDate)
            }

            if(projectLongStart > projectLongEnd) {
                status = true
                binding.projectStartdatum.error = getResources().getString(R.string.start_date_after_end_date)
            }

            return status
        }

        fun projectBudgetValidator(budget: Editable): Boolean {
            var status = false
            val regex = """[0-9.]+""".toRegex()
            val projectBudget = budget

            if(!projectBudget.matches(regex)) {
                val status = true
                binding.projectBudget.error = getResources().getString(R.string.no_allowed_number)
            }

            return status
        }

        viewModel.projectSubmitEvent.observe(viewLifecycleOwner, Observer {
            projectSubmitEvent -> if(projectSubmitEvent) {

                if(projectNaam.isNotBlank() && binding.projectStartdatum.text.isNotBlank() && binding.projectEinddatum.text.isNotBlank() && projectBudget.isNotEmpty() && projectStatus.isNotBlank() && projectType.isNotBlank()) {

                    viewModel.submitProject(
                        projectNaam.toString(),
                        MaterialDateConverter().toDate(binding.projectStartdatum.text.toString()),
                        MaterialDateConverter().toDate(binding.projectEinddatum.text.toString()),
                        projectBudget.toString().toDouble(),
                        projectStatus.toString(),
                        projectType.toString()
                    )
                    Toast.makeText(activity, "Project saved!", Toast.LENGTH_SHORT).show()
                    view?.findNavController()
                        ?.navigate(AddProjectFragmentDirections.actionAddProjectFragmentToProjectOverviewFragment())
                    viewModel.submitEventDone()
                }
                else {
                    Toast.makeText(activity, "Project error!", Toast.LENGTH_SHORT).show()
                    if(projectNaam.isBlank()) {
                        binding.projectNaam.error = getResources().getString(R.string.empty_field)
                    } else {
                        projectNaamValidator(projectNaam)
                    }
                    if(binding.projectStartdatum.text.isBlank()) {
                        binding.projectStartdatum.error = getResources().getString(R.string.empty_field)
                    } else {
                        projectDatesValidator(binding.projectStartdatum.text, binding.projectEinddatum.text)
                    }
                    if(binding.projectEinddatum.text.isBlank()) {
                        binding.projectEinddatum.error = getResources().getString(R.string.empty_field)
                    }
                    if(projectBudget.isEmpty()) {
                        binding.projectBudget.error = getResources().getString(R.string.empty_field)
                    } else {
                        projectBudgetValidator(projectBudget)
                    }
                    if(projectStatus.isBlank()) {
                        binding.projectStatus.error = getResources().getString(R.string.empty_field)
                    }
                    if(projectType.isBlank()) {
                        binding.projectType.error = getResources().getString(R.string.empty_field)
                    }
                }
            }

        })

        return binding.root
    }

}