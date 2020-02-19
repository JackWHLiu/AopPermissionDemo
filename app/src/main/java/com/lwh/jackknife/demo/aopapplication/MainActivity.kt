package com.lwh.jackknife.demo.aopapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn)
        btn!!.setOnClickListener(this)
    }

    @Permission(com.lwh.jackknife.permission.runtime.Permission.CALL_PHONE)
    override fun onClick(view: View) {
        Toast.makeText(this, "想要打通电话给你，我好想好想你！", Toast.LENGTH_SHORT).show()
    }
}