package raywenderlich.com

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    internal val TAG = MainActivity::class.java.simpleName
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var tapMeButton: Button
    internal var score = 0

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountdown: Long = 6000
    internal var countDownInterval: Long = 1000
    internal var timeLeft = 60

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called.")

        // Connects views to variables
        gameScoreTextView = findViewById<TextView>(R.id.game_scor_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        tapMeButton = findViewById<Button>(R.id.tap_me_button)

        tapMeButton.setOnClickListener{ v ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            v.startAnimation(bounceAnimation)

            incrementScore()
        }

        if(savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time left: $timeLeft")
    }

    override fun onDestroy(){
        super.onDestroy()

        Log.d(TAG,"onDestroy called.")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        // Inflate the menu; this adds items to the action bar if it is present
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if(item.itemId == R.id.action_settings){
            showInfo()
        }

        return true
    }

    private fun showInfo(){
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun incrementScore(){
        if(!gameStarted){
            startGame()
        }

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
                // 4
                gameStarted = false

                endGame()
                score = 0
            }
        }
    }

    private fun restoreGame(){
        val restoredScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = restoredScore

        val restoredTime = getString(R.string.time_left, Integer.toString(timeLeft))

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString =  getString(R.string.time_left, Integer.toString(timeLeft))
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish(){
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun startGame(){
        // Start game logic

        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame(){
        // End game logic
        Toast.makeText(this, getString(R.string.game_over_message, Integer.toString(score)), Toast.LENGTH_LONG).show()

    }
}
