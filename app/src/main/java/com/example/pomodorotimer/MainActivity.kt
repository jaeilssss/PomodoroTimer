package com.example.pomodorotimer

import android.annotation.SuppressLint
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {


    private var countDownTimer : CountDownTimer ?=null
    private var tickingSoundId : Int ?=null
    private var bellSoundId : Int ?=null
    private val remainMinutesTextView : TextView by lazy {
        findViewById<TextView>(R.id.remainMinutesTextView)
    }

    private val remainSecondeTextView: TextView by lazy {
        findViewById<TextView>(R.id.remainSecondeTextView)
    }

    private val seekbar : SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekbar)
    }

    private val  soundPool = SoundPool.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()

        initSounds()
    }
    private fun bindViews(){
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, fromUser: Boolean) {
                if(fromUser){
                    updateRemainTime(p1*60*1000L)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                countDownTimer?.cancel()
                countDownTimer =null

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                seekbar ?: return

                startCountDown()
            }

        })
    }

    private fun showTime(){
        var animation = findViewById<LottieAnimationView>(R.id.lottie_view)
        animation.setAnimation(R.raw.fireworksicon)
        animation.playAnimation()
    }
    private fun initSounds(){
        tickingSoundId = soundPool.load(this,R.raw.timer_ticking,1)
        bellSoundId = soundPool.load(this,R.raw.timer_bell,1)
    }
    private fun createCountDownTimer(initialMililis : Long) =
         object : CountDownTimer(initialMililis,1000L){
            override fun onFinish() {
                completeCountDown()
                showTime()
            }

            override fun onTick(p0: Long) {
                updateRemainTime(p0)
                updateSeekBar(p0)
            }

        }
    private fun startCountDown(){
        countDownTimer = createCountDownTimer(seekbar.progress*60*1000L)
        countDownTimer?.start()

        tickingSoundId?.let { soundPool.play(it,1F,1F,0,-1,1F) }
    }
    private fun completeCountDown(){
        updateRemainTime(0)
        updateSeekBar(0)

        bellSoundId?.let { soundPool.play(it,1F,1F,0,0,1F) }
    }
    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis : Long) {
        val remainSeconds = remainMillis / 1000
        remainMinutesTextView.text="%02d".format(remainSeconds/60)
        remainSecondeTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis : Long){
        seekbar.progress = (remainMillis / 1000 / 60).toInt()
    }
    override fun onPause() {
        super.onPause()

        soundPool.autoPause()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onDestroy() {
        super.onDestroy()

        soundPool.release()
    }
}