package com.example.catatankeuangan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import com.example.catatankeuangan.dao.NotesDao
import com.example.catatankeuangan.enumeration.ModeAction
import com.example.catatankeuangan.enumeration.TypeCome
import com.example.catatankeuangan.model.Notes
import com.example.catatankeuangan.view.ListAdapterNotes

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity(intentFor<CreateCatatanActivity>("mode" to ModeAction.CREATE.name))
        }

        val notesDao = NotesDao(this)
        list_notes.adapter = ListAdapterNotes(this, R.layout.item_list_notes, notesDao.listAllNotes())
        list_notes.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            Log.d("LONG CLICK", "on List:" +
                    "Parent ${parent.toString()}" +
                    "Views ${view.toString()}" +
                    "Id ${id.toString()} ")
            val item = list_notes.adapter.getItem(position) as Notes
            val opsi = listOf("Ubah", "Hapus")
            selector("Pilih", opsi, {dialogInterface, i ->
                when (i){
                    0 -> this.startActivity(intentFor<CreateCatatanActivity>(
                            "item" to item,
                            "mode" to ModeAction.EDIT.name
                    ))
                    1 -> {
                        alert ("Anda ingin menghapus data ini?","Hapus"){
                            yesButton {
                                notesDao.deleteNotes(item)
                                list_notes.adapter = ListAdapterNotes(this@MainActivity, R.layout.item_list_notes, notesDao.listAllNotes())
                            }
                            noButton { dialogInterface.dismiss() }
                        }.show()
                    }
                    else -> dialogInterface.dismiss()
                }
            })
            Log.d("ON LONG CLICK", item.toString())
             true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val notesDao = NotesDao(this)
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_outcome -> {
                list_notes.adapter = ListAdapterNotes(this, R.layout.item_list_notes, notesDao.listAllNotes(TypeCome.OUT.name))
                return true
            }
            R.id.action_income -> {
                list_notes.adapter = ListAdapterNotes(this, R.layout.item_list_notes, notesDao.listAllNotes(TypeCome.IN.name))
                return true
            }
            R.id.action_all -> {
                list_notes.adapter = ListAdapterNotes(this, R.layout.item_list_notes, notesDao.listAllNotes())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
