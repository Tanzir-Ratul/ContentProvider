package com.example.contentprovider

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter :BaseAdapter {
   private var  ctx:Context
    private var rs:Cursor
    internal constructor(context: Context, rs:Cursor){
        this.ctx = context
        this.rs = rs
    }
    override fun getCount(): Int {
       return rs.count
    }

    override fun getItem(p0: Int): Any {
        return  p0
    }

    override fun getItemId(p0: Int): Long {
        return  p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val iv = ImageView(ctx)
        rs.moveToPosition(p0)
        val path = rs.getString(0)
        val bitmap = BitmapFactory.decodeFile(path)
        iv.setImageBitmap(bitmap)
        iv.layoutParams = AbsListView.LayoutParams(300,300)
        return iv
    }
}