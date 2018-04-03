package com.example.catatankeuangan

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.example.catatankeuangan.dao.NotesDao
import com.example.catatankeuangan.view.ListAdapterNotes

import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, CreateCatatanActivity::class.java)
            startActivity(intent)
        }

        val listView = findViewById<ListView>(R.id.list_notes)
        val customAdapter = ListAdapterNotes(this, R.layout.item_list_notes)

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
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_outcome -> true
            R.id.action_income -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loadNotesAdapter(cursor: Cursor){
        val from = listOf("item_list_amount", "item_list_date", "item_list_type_outcome", "item_list_type_income")
        val to = listOf(R.id.item_list_amount, R.id.item_list_date, R.id.item_list_type_outcome, R.id.item_list_type_income)
        var fillMaps = ArrayList<HashMap<String, Any>>()
        var map = HashMap<String, Any>()

        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            var note = NotesDao.cursorToNotes(cursor)
            map.put(from[0], note.amount)
            map.put(from[1], note.date)
        }
    }
}
