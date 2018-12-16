package com.ahmednmahran.egoshopping.view.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import java.util.*


/**
 * Created by Ahmed Nabil on 12/16/18.
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private lateinit var onTimeSetListener : TimePickerDialog.OnTimeSetListener
    fun setListener(timeSetListener : TimePickerDialog.OnTimeSetListener) {
        onTimeSetListener = timeSetListener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        onTimeSetListener?.onTimeSet(view,hourOfDay,minute)
    }
}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var onDateSetListener : DatePickerDialog.OnDateSetListener

    fun setListener(dateSetListener : DatePickerDialog.OnDateSetListener) {
        onDateSetListener = dateSetListener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        onDateSetListener?.onDateSet(view,year,month,day)
    }
}