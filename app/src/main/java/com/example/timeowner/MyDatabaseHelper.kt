package com.example.timeowner

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int): SQLiteOpenHelper(context,name,null,version) {

    private val account_creat = "create table accountdata (" +
            "id integer primary key autoincrement," +
            "account text," +
            "email text," +
            "password text)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(account_creat)
        Toast.makeText(context,"Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}