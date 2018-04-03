package com.example.catatankeuangan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.catatankeuangan.dao.NotesDao
import com.example.catatankeuangan.enumeration.TypeCome
import com.example.catatankeuangan.model.Notes
import kotlinx.android.synthetic.main.activity_create_catatan.*
import java.text.SimpleDateFormat
import java.util.*

class CreateCatatanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_catatan)

        val amount = findViewById<EditText>(R.id.amount)
        val date = findViewById<TextView>(R.id.date)
        updateLabel(date, Calendar.getInstance())

        val calendar = Calendar.getInstance()
        val dateDialog = DatePickerDialog.OnDateSetListener(function = { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(date,calendar)
        })

        date.setOnClickListener {
            DatePickerDialog(this,
                    dateDialog,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val notesDao = NotesDao(this)
        save.setOnClickListener {
            Log.d("SAVE","Sedang Menyimpan..")
            val description = findViewById<EditText>(R.id.description).text.toString()
            val outcome = findViewById<RadioButton>(R.id.outcome)
            val typeCome = if (outcome.isChecked) TypeCome.OUT.name else TypeCome.IN.name

            val notes = Notes(
                    amount = amount.text.toString().toDoubleOrNull() ?: 0.0,
                    description = description,
                    date = date.text.toString(),
                    typeCome = typeCome
            )
            Log.d("DEBUG","Notes: "+ notes.toString())

            Toast.makeText(applicationContext, "Sedang menyimpan..."  + notes.toString(), Toast.LENGTH_LONG)
                    .show()
            if (notesDao.insertNotes(notes)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateLabel(textView: TextView, calendar: Calendar){
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        textView.text = sdf.format(calendar.time)
    }
}
