package com.dialog.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.dialog.BDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowDialog.setOnClickListener {
            BDialog.get()
                    .setFullScreen(true)
                    //.setNotFocusable(true)
                    .init { _: BDialog, builder: AlertDialog.Builder ->
                        builder.setTitle("Test dialog")
                        builder.setMessage("Hello world!Hello world!")
                    }
                    .show(lifecycle, supportFragmentManager, "TestDialog")
        }
    }
}
