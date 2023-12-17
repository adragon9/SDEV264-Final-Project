package com.example.basal_dist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val close = findViewById<FloatingActionButton>(R.id.fab_help_close)
        val settings = findViewById<FloatingActionButton>(R.id.fab_settings)
        val helpText = findViewById<TextView>(R.id.help_text1)
        val rightArrow = findViewById<TextView>(R.id.help_right)
        val leftArrow = findViewById<TextView>(R.id.help_left)
        val imageViewer = findViewById<ImageView>(R.id.imageView)
        val imageDesc = findViewById<TextView>(R.id.help_displayNotes)

        val helpStrings = arrayListOf(
            getString(R.string.help1),
            getString(R.string.help2),
            getString(R.string.help3),
            getString(R.string.help4),
            getString(R.string.help5)
        )

        val imageDescStrings = arrayListOf(
            getString(R.string.helpImageDesc1),
            getString(R.string.helpImageDesc2),
            getString(R.string.helpImageDesc3),
            getString(R.string.helpImageDesc4),
            getString(R.string.helpImageDesc5)
        )

        var index=0
        var text = helpText.text.toString()
        imageViewer.setImageResource(R.drawable.freestyle_app)
        imageDesc.text = imageDescStrings[0]

        rightArrow.setOnClickListener {
            index += 1
            if (index < helpStrings.size) {
                helpText.text = helpStrings[index]
            } else {
                index = 0
                helpText.text = helpStrings[index]
            }
            text = helpText.text.toString()
            changeDisplay(helpStrings, text, imageViewer, imageDesc, imageDescStrings)
        }

        leftArrow.setOnClickListener {
            index -= 1
            if (index >= 0) {
                helpText.text = helpStrings[index]
            } else {
                index = helpStrings.size - 1
                helpText.text = helpStrings[index]
            }
            text = helpText.text.toString()
            changeDisplay(helpStrings, text, imageViewer, imageDesc, imageDescStrings)
        }

        close.setOnClickListener {
            val close_intent = Intent(this, MainActivity::class.java)
            startActivity(close_intent)
        }

        settings.setOnClickListener {
            val settings_intent = Intent(this, Settings::class.java)
            startActivity(settings_intent)
        }
    }
    private fun changeDisplay(helpStrings: ArrayList<String>, text: String, imageViewer: ImageView,
                              displayDesc: TextView, imageDescStrings: ArrayList<String>){
        when(text) {
            helpStrings[0] -> imageViewer.setImageResource(R.drawable.freestyle_app)
            helpStrings[1] -> imageViewer.setImageResource(R.drawable.where_to_begin)
            helpStrings[2] -> imageViewer.setImageResource(R.drawable.what_data_to_use)
            helpStrings[3] -> imageViewer.setImageResource(R.drawable.where_to_enter)
            helpStrings[4] -> imageViewer.setImageResource(R.drawable.error_margin)
        }
        when(text){
            helpStrings[0] -> displayDesc.text = imageDescStrings[0]
            helpStrings[1] -> displayDesc.text = imageDescStrings[1]
            helpStrings[2] -> displayDesc.text = imageDescStrings[2]
            helpStrings[3] -> displayDesc.text = imageDescStrings[3]
            helpStrings[4] -> displayDesc.text = imageDescStrings[4]
        }
    }
}