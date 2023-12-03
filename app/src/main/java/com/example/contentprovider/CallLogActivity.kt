package com.example.contentprovider

import android.Manifest
import android.content.ContentProvider
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.Settings
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.cursoradapter.widget.SimpleCursorAdapter
import timber.log.Timber

class CallLogActivity : AppCompatActivity() {

    private val column = listOf(
        CallLog.Calls._ID,
        CallLog.Calls.NUMBER,
        CallLog.Calls.TYPE,
        CallLog.Calls.DURATION,
    ).toTypedArray()

    private var listview: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)

        listview = findViewById<ListView>(R.id.listView)
        runtimePermission()
    }

    private fun runtimePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CALL_LOG
                )
            ) {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Permission")
                alertDialog.setCancelable(false)
                alertDialog.setMessage("This permission is needed to read call log")
                alertDialog.setPositiveButton("ok") { dialog, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        Array(1) { Manifest.permission.READ_CALL_LOG },
                        200
                    )
                    dialog.dismiss()

                }

                alertDialog.show()

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    Array(1) { Manifest.permission.READ_CALL_LOG },
                    200
                )
            }

        } else {
            displayLog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayLog()
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CALL_LOG
                )
            ) {
                val alertBuilder = AlertDialog.Builder(this)
                alertBuilder.setMessage("This permission is needed to read call log you can enable it from setting")
                alertBuilder.setCancelable(false)
                alertBuilder.setPositiveButton("ok") { dialog, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    dialog.dismiss()
                }
                alertBuilder.show()
            } else {
                runtimePermission()
            }
    }


    private fun displayLog() {

        val from = listOf(
            CallLog.Calls.NUMBER,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE,
        ).toTypedArray()
        val to = intArrayOf(R.id.numberTV, R.id.durationTV, R.id.typeTV)
        val resultSet = contentResolver.query(
            CallLog.Calls.CONTENT_URI, column, null, null,
            "${CallLog.Calls.LAST_MODIFIED} DESC"
        )

        val adapter = SimpleCursorAdapter(this, R.layout.call_item_card, resultSet, from, to, 0)
        listview?.adapter = adapter


    }
}


//1 = incomming
//2 = outgoing
//3 = missed call
//4 = voice main
//5 = rejected call
//6 = refused(block number)