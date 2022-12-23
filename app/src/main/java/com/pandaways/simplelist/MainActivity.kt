package com.pandaways.simplelist

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.pandaways.simplelist.adapter.PersonAdapter
import com.pandaways.simplelist.adapter.viewholder.PersonViewHolder
import com.pandaways.simplelist.data.PersonMock
import com.pandaways.simplelist.databinding.ActivityMainBinding
import com.pandaways.simplelist.model.PersonUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val personAdapter = PersonAdapter(object : PersonViewHolder.Listener {
        override fun onItemClicked(item: PersonUiModel) {
            showSnackBar("${item.name} clicked")
        }
    })

    private val personList: List<PersonUiModel> by lazy { PersonMock.personList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        binding.recyclerView.adapter = personAdapter
        personAdapter.setItems(personList)

        binding.etSearchName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                searchByNameJob.cancel()
                searchByNameJob = searchByName(p0.toString())
            }
        })

        binding.btnStart.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                mStartDate = selectedDate
                binding.btnStart.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(selectedDate)
            }
        }
        binding.btnTo.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                mEndDate = selectedDate
                binding.btnTo.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(selectedDate)

                searchByDateJob.cancel()
                searchByDateJob = searchByDateRange()
            }
        }
    }

    private var searchByNameJob: Job = Job()
    private fun searchByName(query: String) = lifecycleScope.launch {
        delay(1000)
        val filteredPersons = personList.filter { it.name.contains(query, true) }
        personAdapter.setItems(filteredPersons)
        personAdapter.notifyDataSetChanged()
    }

    private var searchByDateJob: Job = Job()
    private var mStartDate: Date? = null
    private var mEndDate: Date? = null
    private fun searchByDateRange() = lifecycleScope.launch {
        delay(1000)
        if (mStartDate == null) showSnackBar("Choose start date first.")
        val startDate = mStartDate ?: return@launch
        val endDate = mEndDate ?: return@launch

        val filteredPersons = personList.filter {
            it.birthDate.after(startDate) && it.birthDate.before(endDate)
        }
        personAdapter.setItems(filteredPersons)
        personAdapter.notifyDataSetChanged()
    }

    private fun showDatePickerDialog(onDateSelected: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this,
            { _, year, monthOfYear, dayOfMonth -> onDateSelected("$year-${(monthOfYear + 1)}-$dayOfMonth".toDate()) },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun String.toDate(): Date {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return format.parse(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            /* view = */ findViewById(android.R.id.content),
            /* text = */ message,
            /* duration = */ Snackbar.LENGTH_SHORT
        ).show()
    }
}