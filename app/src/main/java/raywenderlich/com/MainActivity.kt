package raywenderlich.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var tapMeButton: Button
    internal var score = 0

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountdown: Long = 6000
    internal var countDownInterval: Long = 1000
    internal var timeLeft = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connects views to variables
        gameScoreTextView = findViewById<TextView>(R.id.game_scor_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        tapMeButton = findViewById<Button>(R.id.tap_me_button)

        tapMeButton.setOnClickListener{ v -> incrementScore() }

        resetGame()
    }

    private fun incrementScore(){
        // Increment score logic
        score++

        val newScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = newScore
    }

    private fun resetGame(){
        // Reset game logic
        // 1
        score = 0

        val initialScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, Integer.toString(60))
        timeLeftTextView.text = initialTimeLeft

        // 2
        countDownTimer = object : CountDownTimer(initialCountdown, countDownInterval){
            //3
            override fun onTick(millisUntilFinished: Long){
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, Integer.toString(timeLeft))
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish(){
                // To be implemented later
            }
        }

        // 4
        gameStarted = false
    }

    private fun startGame(){
        // Start game logic

    }

    private fun endGame(){
        // End game logic

    }
}
