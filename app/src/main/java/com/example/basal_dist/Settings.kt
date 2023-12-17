package com.example.basal_dist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.pow

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val close = findViewById<FloatingActionButton>(R.id.fab_settings_close)
        val help = findViewById<FloatingActionButton>(R.id.fab_help)
        val submit = findViewById<Button>(R.id.btn_submit_settings)
        val warning = findViewById<TextView>(R.id.warningDaily)
        val labels = arrayListOf<TextView>(
            findViewById(R.id.lbl_setting_daily),
            findViewById(R.id.lbl_setting_hourly))

        val editBoxes = arrayListOf<EditText>(
            findViewById(R.id.edit_daily),
            findViewById(R.id.edit_hourly))

        for(i in 0 until editBoxes.size){
            editBoxes[i].hint = "Current: "+getSettings(labels[i].text.toString())
        }

        val daily = getSettings(labels[0].text.toString())
        var temp = daily.toString().toDouble() % 3.0
        if(temp != 0.0 && daily.toString().isNotEmpty()){
            warning.text = getString(R.string.remainder_err)
        } else {
            warning.text = ""
        }

        submit.setOnClickListener {
            for(i in 0 until editBoxes.size){
                if(editBoxes[i].text.isNotEmpty()) {
                    editBoxes[i].text.toString().toDouble()
                    saveSetting(labels[i].text.toString(), editBoxes[i].text.toString())
                    Snackbar.make(submit, "Settings Saved", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        close.setOnClickListener {
            val close_intent = Intent(this, MainActivity::class.java)
            startActivity(close_intent)
        }

        help.setOnClickListener {
            val help_intent = Intent(this, Help::class.java)
            startActivity(help_intent)
        }
    }
    private fun saveSetting(id:String, userVal:String){
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString(id, userVal)

        editor.apply()
    }

    private fun getSettings(id:String): String? {
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        return sharedPref.getString(id, "")
    }
}