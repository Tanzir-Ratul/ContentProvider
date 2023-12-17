package com.example.contentprovider

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.GridView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class LoadImagesActivity : AppCompatActivity() {
    private lateinit var  res: Cursor
    private lateinit var gridView: GridView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loadimages)
        gridView = findViewById(R.id.gridView)
        runtimePermission()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun runtimePermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,Array(1){Manifest.permission.READ_EXTERNAL_STORAGE},301)
        }else{
            loadImages()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 301 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            loadImages()
        }else{

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadImages() {

        val col = listOf<String>(MediaStore.Images.Thumbnails.DATA).toTypedArray()
         res =  contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,col,null,null,null)!!
        /*if(res?.moveToNext() == true){
            Toast.makeText(this, res.getString(0), Toast.LENGTH_SHORT).show()
        }*/
        gridView.adapter = ImageAdapter(this@LoadImagesActivity, this.res)

    }
}