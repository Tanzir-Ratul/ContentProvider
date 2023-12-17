package com.example.contentprovider

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contentprovider.databinding.ActivityCustomProviderBinding

class CustomProvider : AppCompatActivity() {
    lateinit var binding: ActivityCustomProviderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* val helper = MyHelper(applicationContext)
         val db = helper.readableDatabase
         val rs = db.rawQuery("SELECT * FROM Test",null)
         if(rs.moveToFirst()){
             Toast.makeText(this, "${rs.getString(1)}\n${rs.getString(2)}", Toast.LENGTH_SHORT)
                 .show()
         }*/
        val rs = contentResolver.query(
            MyProvider.CONTENT_URI,
            arrayOf(MyProvider._ID, MyProvider.NAME, MyProvider.MEANING),
            null,
            null,
            MyProvider.NAME
        )

        binding.nextBtn.setOnClickListener {
            if (rs?.moveToNext() == true) {
                binding.nameET.setText(rs.getString(1))
                binding.meaningET.setText(rs.getString(2))
            }
        }

        binding.clearBtn.setOnClickListener {
            binding.nameET.text.clear()
            binding.meaningET.text.clear()
            binding.nameET.requestFocus()
        }

        binding.prevBtn.setOnClickListener {
            if (rs?.moveToPrevious() == true) {
                binding.nameET.setText(rs.getString(1))
                binding.meaningET.setText(rs.getString(2))
            }
        }
        binding.insertBtn.setOnClickListener {
            val cv = ContentValues()
            cv.put(MyProvider.NAME, binding.nameET.text.toString())
            cv.put(MyProvider.MEANING, binding.meaningET.text.toString())
            contentResolver.insert(MyProvider.CONTENT_URI, cv)
            rs?.requery()
        }
        binding.updateBtn.setOnClickListener {
            val cv = ContentValues()
            cv.put(MyProvider.NAME, binding.nameET.text.toString())
            cv.put(MyProvider.MEANING, binding.meaningET.text.toString())
            contentResolver.update(
                MyProvider.CONTENT_URI,
                cv,
                "NAME = ?",
                arrayOf(binding.nameET.text.toString())
            )
            rs?.requery()
        }

        binding.deleteBtn.setOnClickListener {
            contentResolver.delete(
                MyProvider.CONTENT_URI, "NAME = ?", arrayOf(binding.nameET.text.toString())
            )
            rs?.requery()
        }
    }
}