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
import com.example.catatankeuangan.enumeration.ModeAction
import com.example.catatankeuangan.enumeration.TypeCome
import com.example.catatankeuangan.model.Notes
import kotlinx.android.synthetic.main.activity_create_catatan.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class CreateCatatanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_catatan)

        updateLabel(date, Calendar.getInstance())
        val calendar = Calendar.getInstance()
        val dateDialog = DatePickerDialog.OnDateSetListener(function = { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(date,calendar)
        })

        val item:Notes? = intent.getParcelableExtra<Notes>("item")
        val mode:String? = intent.getStringExtra("mode")
        item?.let { setFormNotes(item) }
        mode?.let { Log.d("MODE", mode) }

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
            val description = description.text.toString()
            val outcome = outcome
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
            when(mode){
                ModeAction.CREATE.name -> {
                    if (notesDao.insertNotes(notes)){
                        startActivity(intentFor<MainActivity>())
                        toast("Data berhasil disimpan")
                    }
                }
                ModeAction.EDIT.name -> {
                    if (item is Notes) {
                        notes.id = item.id
                        if(notesDao.updateNotes(notes)){
                            startActivity(intentFor<MainActivity>())
                            toast("Data berhasil diupdate")
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateLabel(textView: TextView, calendar: Calendar){
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        textView.text = sdf.format(calendar.time)
    }

    private fun setFormNotes(notes: Notes){
        amount.setText(String.format("%.2f", notes.amount))
        description.setText(notes.description)
        date.text = notes.date
        when(notes.typeCome){
            TypeCome.OUT.name -> typeCome.check(R.id.outcome)
            TypeCome.IN.name -> typeCome.check(R.id.income)
        }
    }
}
