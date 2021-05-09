package com.example.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView : TextView by lazy {
        findViewById<TextView>(R.id.remainMinutesTextView)
    }

    private val remainSecondeTextView: TextView by lazy {
        findViewById<TextView>(R.id.remainSecondeTextView)
    }

    private val seekbar : SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekbar)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

    }

    private fun bindViews(){
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    remainMinutesTextView.text = "%02d".format(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }
}