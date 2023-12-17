package com.example.basal_dist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class SecondaryActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        val glucoseAvgs = arrayListOf<Int>(
            intent.getIntExtra("bg_1", 0),
            intent.getIntExtra("bg_2", 0),
            intent.getIntExtra("bg_3", 0),
            intent.getIntExtra("bg_4", 0),
            intent.getIntExtra("bg_5", 0),
            intent.getIntExtra("bg_6", 0),
            intent.getIntExtra("bg_7", 0),
            intent.getIntExtra("bg_8", 0))

        val close = findViewById<FloatingActionButton>(R.id.fab_close)
        val help = findViewById<FloatingActionButton>(R.id.fab_help)
        val settings = findViewById<FloatingActionButton>(R.id.fab_settings)

        val resultViews = arrayListOf<TextView>(
            findViewById(R.id.txt_result_1),
            findViewById(R.id.txt_result_2),
            findViewById(R.id.txt_result_3),
            findViewById(R.id.txt_result_4),
            findViewById(R.id.txt_result_5),
            findViewById(R.id.txt_result_6),
            findViewById(R.id.txt_result_7),
            findViewById(R.id.txt_result_8))

        var weightedValues = arrayListOf<Double>()
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val str_dailyBasal = sharedPref.getString("Daily Basal", "0.0")
        val str_maxHourly = sharedPref.getString("Max Hourly Basal", "0.0")
        // Converts strings to numbers
        val dailyBasal = str_dailyBasal!!.toDouble()
        val maxHourly = str_maxHourly!!.toDouble()
        // adds the averages together
        val avgsTotal = sumOf(glucoseAvgs)
        // Divides each average by the total to get their percentage value of the total
        for(element in glucoseAvgs){
            weightedValues.add(element.toDouble()/avgsTotal)
        }
        // Multiplies the percentages by the daily basal then divides by 3
        // to get the weighted hourly value
        for(i in 0 until weightedValues.size){
            weightedValues[i] = (weightedValues[i] * dailyBasal) / 3
        }
        // Distributes overflow for values that exceed the maximum hourly value
        var overflow = 0.0
        var numOver = 0
        for(i in 0 until weightedValues.size){
            if(weightedValues[i] > maxHourly){
                overflow += weightedValues[i] - maxHourly
                numOver += 1
                weightedValues[i] = maxHourly
            }
        }

        for(i in 0 until weightedValues.size){
            if (weightedValues.size < maxHourly){
                weightedValues[i] += overflow/(weightedValues.size - numOver)
            }
        }
        // The pump only allows 2 decimal places of precision this makes sure that
        // the values add up to the daily basal values
        var distributionAccurate = false
        while(!distributionAccurate) {
            // Resets the values for recalculation
            var lowestValue = maxHourly + 1
            var highestValue = 0.0
            var currentTotalInsulin = 0.0
            // Rounds values to 2 decimal places
            for (i in 0 until weightedValues.size) {
                weightedValues[i] = (weightedValues[i] * 100.0).roundToInt() / 100.0
                currentTotalInsulin += weightedValues[i]
            }
            // Must multiply by 3 to check to see if values add up to daily basal amount
            currentTotalInsulin *= 3
            // Checks to see how many values reached the maximum hourly rate
            var maxHourlyCount = 0
            for(i in 0 until weightedValues.size){
                if(weightedValues[i] == maxHourly){
                    maxHourlyCount += 1
                }
            }
            // Checks to see whether everything adds up to the daily basal value
            // then adds or subtracts based on whether the value is higher or lower
            var dailyBasalDiff = dailyBasal - currentTotalInsulin
            if(currentTotalInsulin == dailyBasal || maxHourlyCount == 8 || dailyBasalDiff < .02){
                distributionAccurate = true
                currentTotalInsulin = (currentTotalInsulin * 100.0).roundToInt() / 100.0
                if(maxHourlyCount == 8){
                    findViewById<TextView>(R.id.sumView).text = getString(R.string.maxed_out)+" "+currentTotalInsulin.toString()
                } else {
                    findViewById<TextView>(R.id.sumView).text = getString(R.string.sum) + " " + currentTotalInsulin.toString()
                }
            } else if(currentTotalInsulin > dailyBasal){
                for(i in 0 until weightedValues.size){
                    if(weightedValues[i] > highestValue && weightedValues[i] != maxHourly){
                        highestValue = weightedValues[i]
                    }
                }
                for(i in 0 until weightedValues.size){
                    if(weightedValues[i] == highestValue){
                        weightedValues[i] = highestValue - .01
                        break
                    }
                }
            } else {
                for(i in 0 until weightedValues.size){
                    if(weightedValues[i] < lowestValue && weightedValues[i] != maxHourly){
                        lowestValue = weightedValues[i]
                    }
                }
                for(i in 0 until weightedValues.size){
                    if(weightedValues[i] == lowestValue){
                        weightedValues[i] = lowestValue + .01
                        break
                    }
                }
            }
        }

        resultViews[0].text = weightedValues[0].toString()
        resultViews[1].text = weightedValues[1].toString()
        resultViews[2].text = weightedValues[2].toString()
        resultViews[3].text = weightedValues[3].toString()
        resultViews[4].text = weightedValues[4].toString()
        resultViews[5].text = weightedValues[5].toString()
        resultViews[6].text = weightedValues[6].toString()
        resultViews[7].text = weightedValues[7].toString()

        close.setOnClickListener{
            val close = Intent(this, MainActivity::class.java)
            startActivity(close)
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

    private fun sumOf(array:ArrayList<Int>): Int{
        var sum = 0
        for(element in array){
            sum += element
        }
        return sum
    }
}