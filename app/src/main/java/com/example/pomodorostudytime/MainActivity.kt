package com.example.pomodorostudytime

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // UI elements in use for timer
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private lateinit var timerTextView: TextView

    // Timer elements and values
    // Contains the logic for counting down the time
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var timeLeftInMilis: Long = 25 * 60 * 1000

    // Used for the layout of the app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            startTimer()
        }
        pauseButton.setOnClickListener {
            pauseTimer()
        }
        resetButton.setOnClickListener {
            resetTimer()
        }

        updateCountdownText()
    }

    private fun startTimer(){
        countDownTimer = object : CountDownTimer(timeLeftInMilis, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMilis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                isTimerRunning = false
                timerTextView.text = "Done!"
            }
        }.start()

        isTimerRunning = true
        startButton.visibility = Button.GONE
        pauseButton.visibility = Button.VISIBLE
    }

    private fun pauseTimer(){
        // stop timer/null does nothing
        countDownTimer?.cancel()
        isTimerRunning = false
        startButton.visibility = Button.VISIBLE
        pauseButton.visibility = Button.GONE
    }

    private fun resetTimer(){
        countDownTimer?.cancel()
        timeLeftInMilis = 25 * 60 * 1000
        isTimerRunning = false
        updateCountdownText()
        startButton.visibility = Button.VISIBLE
        pauseButton.visibility = Button.GONE
    }

    private fun updateCountdownText(){
        val minutes = (timeLeftInMilis / 1000) / 60
        val seconds = (timeLeftInMilis / 1000) % 60
        // formats the time into a string
        val formatTime = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = formatTime
    }
}