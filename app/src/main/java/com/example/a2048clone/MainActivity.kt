package com.example.a2048clone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button

// Debug tag
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(),
                     GestureDetector.OnGestureListener,
                     GestureDetector.OnDoubleTapListener
{
    // Gesture Detector instance
    private lateinit var mDetector: GestureDetectorCompat

    // Game singleton instance
    private val game = Game

    private lateinit var board : Array< Array<Button> >

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar
        supportActionBar?.hide()

        // Hide the UI
        hideUI()

        // Create View Matrix for board
        val row0 = arrayOf(textView0,textView1,textView2,textView3)
        val row1 = arrayOf(textView4,textView5,textView6,textView7)
        val row2 = arrayOf(textView8,textView9,textView10,textView11)
        val row3 = arrayOf(textView12,textView13,textView14,textView15)
        board = arrayOf(row0,row1,row2,row3)

        // Set the initial UI
        updateUI()

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = GestureDetectorCompat(this, this)
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this)

    }

    // Activate immersive mode through flags
    private fun hideUI()
    {
        // Immersive mode
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    // Update the UI
    fun updateUI()
    {
        for(row in 0..3)
        {
            for(col in 0..3)
            {
                val str = "" + game.getTileValue(row,col)
                board[row][col].text = str
            }
        }
    }

    // Override the onTouchEven to send it to mDetector
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    // Detect the direction of a swipe motion from the user
    override fun onFling(swipe1: MotionEvent?, swipe2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean
    {
        // Determine the direction using the getSlope method
        when(getSlope(swipe1!!.x, swipe1.y, swipe2!!.x,swipe2.y))
        {
            1 -> {
                game.upSwipe(this)
                Log.d(TAG, "MAIN_ACT: UP SWIPE")
            }
            2 -> {
                game.leftSwipe(this)
                Log.d(TAG, "MAIN_ACT: LEFT SWIPE")
            }
            3 -> {
                game.downSwipe(this)
                Log.d(TAG, "MAIN_ACT: DOWN SWIPE")
            }
            4 -> {
                game.rightSwipe(this)
                Log.d(TAG, "MAIN_ACT: RIGHT SWIPE")
            }
        }

        // We return false because we don't want the event to be consumed
        return false
    }

    // Slope finder helper method
    // 1 -> Up
    // 2 -> Left
    // 3 -> Down
    // 4 -> Right
    private fun getSlope(x1: Float, y1: Float, x2: Float, y2: Float): Int
    {
        val angle = Math.toDegrees(Math.atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()))
        // Up
        if (angle > 45 && angle <= 135) return 1
        // Left
        else if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) return 2
        // Down
        if (angle < -45 && angle >= -135)  return 3
        // Right
        if (angle > -45 && angle <= 45) return 4
        return 0
    }

    // Gesture methods we aren't implementing
    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean { return true }
    override fun onShowPress(p0: MotionEvent?) {}
    override fun onLongPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?): Boolean { return true }
    override fun onDown(p0: MotionEvent?): Boolean { return true }
    override fun onDoubleTap(p0: MotionEvent?): Boolean { return true }
    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean { return true }
    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean { return true }
}
