package com.salaheddin.fallingwords.ui.gameScreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.salaheddin.fallingwords.R
import com.salaheddin.fallingwords.utils.Timer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GameActivity : AppCompatActivity() {
    private val viewModel: GameViewModel by viewModel()
    //private lateinit var animation: TranslateAnimation
    //private lateinit var anim: ObjectAnimator

    private lateinit var timer: Timer
    private val time = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTimer()
        //setUpAnimation()
        setClickListener()
        setObservers()
    }

    private fun setObservers() {
        viewModel.currentWord.observe(this) { word ->
            tvCurrentWord.text = word.eng
        }

        viewModel.fallingWord.observe(this) { word ->
            tvFallingWord.text = word.spa
        }

        viewModel.score.observe(this) { score ->
            tvScore.text = score.toString()
        }

        viewModel.isGameOver.observe(this) { isGameOver ->
            if (isGameOver) {
                timer.stop()

                AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setMessage("You have lost your score is ${viewModel.score.value}")
                    .setPositiveButton("Restart", { dialog, which -> restart() })
                    .setNegativeButton("Exit", null)
                    .show()
            }
        }

        viewModel.hasWon.observe(this) { isGameOver ->
            if (isGameOver) {
                timer.stop()
                tvFallingWord.visibility = View.GONE

                AlertDialog.Builder(this)
                    .setTitle("You Won")
                    .setMessage("Congratulations you have completed all the words and your score is ${viewModel.score.value}")
                    .setPositiveButton("Restart", { dialog, which -> restart() })
                    .setNegativeButton("OK", null)
                    .show()
            }
        }
    }

    private fun setClickListener() {
        ivWrong.setOnClickListener {
            viewModel.submitWrongAction()
            timer.restartTimer()
        }

        ivCorrect.setOnClickListener {
            viewModel.submitCorrectAction()
            timer.restartTimer()
        }

        ivRestart.setOnClickListener {
            restart()
        }

        ivPause.setOnClickListener {
            if (timer.isPaused) {
                ivPause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause))
                timer.resume()
            } else {
                ivPause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play))
                timer.pause()
            }
        }
    }

    private fun setUpTimer() {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        root.post {
            vTopViews.measure(0, 0)
            vBottomViews.measure(0, 0)
            val height = screenHeight - vTopViews.measuredHeight - vBottomViews.measuredHeight
            timer = Timer(time, 10) { milliseconds ->
                tvTimer.text = String.format("%.1f", ((time.toFloat() - milliseconds.toFloat()) / 1000))
                val per = (milliseconds.toFloat() / time.toFloat()) * 100
                pbTimer.progress = per.toInt()
                tvFallingWord.translationY = (height * per) / 100

                if (time == milliseconds) {
                    viewModel.setGameOver()
                }
            }
            timer.startTimer()
        }
    }

    /*private fun setUpAnimation() {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        *//*root.post {
            vTopViews.measure(0,0)
            val topViewsHeight = vTopViews.measuredHeight
            tvFallingWord.addTextChangedListener {
                tvFallingWord.measure(0, 0)
                anim = ObjectAnimator.ofFloat(tvFallingWord, "y", topViewsHeight.toFloat(), screenHeight - (tvFallingWord.measuredHeight * 2))
                anim.duration = time

                anim.start()
            }
        }*//*
    }*/

    private fun restart() {
        viewModel.restart()
        timer.restartTimer()
        tvFallingWord.visibility = View.VISIBLE
    }

    /*private fun setUpAnimation() {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()

        tvFallingWord.addTextChangedListener {
            tvFallingWord.visibility = View.VISIBLE
            tvFallingWord.measure(0, 0)
            var x = (screenWidth / 2) - (tvFallingWord.measuredWidth / 2)
            x = if (x < 0 || x > screenWidth) 0.0f else x

            animation =
                TranslateAnimation(x, x, 0.0f, screenHeight - (tvFallingWord.measuredHeight * 2))
            animation.fillAfter = true
            animation.duration = time

            tvFallingWord.clearAnimation()
            tvFallingWord.startAnimation(animation)
        }
    }*/
}