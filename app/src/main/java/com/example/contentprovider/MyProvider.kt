package com.example.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyProvider : ContentProvider() {

    private lateinit var db: SQLiteDatabase

    companion object {
        val PROVIDER_NAME = "com.example.contentprovider/MyProvider"
        val URL = "content://$PROVIDER_NAME/Test"
        val CONTENT_URI = Uri.parse(URL)
        val _ID = "_id"
        val NAME = "Name"
        val MEANING = "Meaning"

    }

    override fun onCreate(): Boolean {
        val helper = MyHelper(context)
        db = helper.writableDatabase
        return true
    }


    override fun getType(p0: Uri): String? {
       return "vnd.android.cursor.dir/vnd.example.test"
    }

    override fun insert(uri: Uri, cv: ContentValues?): Uri? {
        db.insert("Test", null, cv)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, condition: String?, conditionValue: Array<out String>?): Int {
        val count = db.delete("Test", condition, conditionValue)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        p1: ContentValues?,
        condition: String?,
        conditionValue: Array<out String>?
    ): Int {
        val count = db.update("Test", p1, condition, conditionValue)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun query(
        uri: Uri,
        cols: Array<out String>?,
        condition: String?,
        conditionValue: Array<out String>?,
        orderBy: String?
    ): Cursor? {
        return db.query("Test", cols, condition, conditionValue, null, null, orderBy)

    }
}