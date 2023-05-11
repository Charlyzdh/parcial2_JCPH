package com.example.parcial2moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToActivityTwo(view: View) {
        val intent = Intent(this, ActivityGame::class.java)
        startActivity(intent)
    }
}