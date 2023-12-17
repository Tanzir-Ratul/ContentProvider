package com.example.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyHelper(context:Context?):SQLiteOpenHelper(context,"ABCD",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
         db?.execSQL("CREATE TABLE Test (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Meaning TEXT)")
         db?.execSQL("INSERT INTO Test (Name,Meaning) VALUES('MCA', 'Master of computer Application')")
         db?.execSQL("INSERT INTO Test (Name,Meaning) VALUES('CA', 'Chartered Account')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}