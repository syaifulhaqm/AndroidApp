package com.example.catatankeuangan.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataHelper (context:Context, createQuery:String, dropQuery:String) :SQLiteOpenHelper (context, DATABASE_NAME, null, 1){
    companion object {
        const val DATABASE_NAME = "finance_notes.db"
    }

    private val create = createQuery
    private val drop = dropQuery

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            it.execSQL(create)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.execSQL(drop)
            onCreate(it)
        }
    }

}