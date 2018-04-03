package com.example.catatankeuangan.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.catatankeuangan.model.Notes

class NotesDao constructor(context: Context){
    companion object {
        const val NOTES_TABLE_NAME = "notes"
        const val NOTES_COLUMN_ID = "nt_id"
        const val NOTES_COLUMN_AMOUNT = "nt_amount"
        const val NOTES_COLUMN_TYPE = "nt_type"
        const val NOTES_COLUMN_DATE = "nt_date"
        const val NOTES_COLUMN_DESCRIPTION = "nt_description"
        val notesAllColumn = listOf(
                NOTES_COLUMN_ID, NOTES_COLUMN_AMOUNT, NOTES_COLUMN_TYPE, NOTES_COLUMN_DATE, NOTES_COLUMN_DESCRIPTION
        )

        fun cursorToNotes(cursor: Cursor):Notes = cursorToNotes(cursor)
    }

    private val createTableNotes = """
        CREATE TABLE $NOTES_TABLE_NAME(
            $NOTES_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $NOTES_COLUMN_AMOUNT REAL,
            $NOTES_COLUMN_TYPE TEXT,
            $NOTES_COLUMN_DATE TEXT,
            $NOTES_COLUMN_DESCRIPTION TEXT
        )
    """
    private val dropTableNotes = """DROP TABLE IF EXISTS $NOTES_TABLE_NAME"""

    var db:SQLiteDatabase
    val  dbHelper = DataHelper(context, createTableNotes, dropTableNotes)

    init {
       db = dbHelper.readableDatabase
    }

    fun insertNotes(notes: Notes):Boolean {
        db = dbHelper.writableDatabase
        Log.d("INSERT", notes.toString())

        val value = ContentValues()
        value.put(NOTES_COLUMN_AMOUNT, notes.amount)
        value.put(NOTES_COLUMN_TYPE, notes.typeCome)
        value.put(NOTES_COLUMN_DATE, notes.date)
        value.put(NOTES_COLUMN_DESCRIPTION, notes.description)
        db.insert(NOTES_TABLE_NAME, null,value)
        Log.d("INSERT","Sukses menyimpan")
        return true
    }

    fun listAllNotes(){
        db = dbHelper.readableDatabase
        // SELECT ALL SQL QUERY
        val cursor:Cursor = db.query(NOTES_TABLE_NAME, notesAllColumn)
        cursor.moveToFirst()


    }

    fun cursorToNotes(cursor: Cursor): Notes {
        return Notes(cursor.getLong(0),
                cursor.getDouble(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        )
    }
}