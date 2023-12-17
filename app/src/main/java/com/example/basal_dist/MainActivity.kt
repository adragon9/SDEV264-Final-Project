package com.example.basal_dist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submit = findViewById<Button>(R.id.btn_submit)
        val help = findViewById<FloatingActionButton>(R.id.fab_help)
        val settings = findViewById<FloatingActionButton>(R.id.fab_settings)
        val inputArray = arrayListOf<EditText>(
            findViewById(R.id.edit_timeblock_1),
            findViewById(R.id.edit_timeblock_2),
            findViewById(R.id.edit_timeblock_3),
            findViewById(R.id.edit_timeblock_4),
            findViewById(R.id.edit_timeblock_5),
            findViewById(R.id.edit_timeblock_6),
            findViewById(R.id.edit_timeblock_7),
            findViewById(R.id.edit_timeblock_8)
        )

        submit.setOnClickListener {
            var emptyBoxes: Boolean? = true
            for(i in 0 until inputArray.size) {
                if (inputArray[i].text.toString() == "") {
                    emptyBoxes = true
                    inputArray[i].setHint(R.string.val_err)
                    break
                } else {
                    emptyBoxes = false
                }
            }
            if(emptyBoxes == false){
                val intent = Intent(this, SecondaryActivity::class.java)
                intent.putExtra("bg_1", inputArray[0].text.toString().toInt())
                intent.putExtra("bg_2", inputArray[1].text.toString().toInt())
                intent.putExtra("bg_3", inputArray[2].text.toString().toInt())
                intent.putExtra("bg_4", inputArray[3].text.toString().toInt())
                intent.putExtra("bg_5", inputArray[4].text.toString().toInt())
                intent.putExtra("bg_6", inputArray[5].text.toString().toInt())
                intent.putExtra("bg_7", inputArray[6].text.toString().toInt())
                intent.putExtra("bg_8", inputArray[7].text.toString().toInt())
                startActivity(intent)
            }
        }

        help.setOnClickListener {
            val help_intent = Intent(this, Help::class.java)
            startActivity(help_intent)
        }

        settings.setOnClickListener {
            val settings_intent = Intent(this, Settings::class.java)
            startActivity(settings_intent)
        }
    }
}