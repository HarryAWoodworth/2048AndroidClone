package com.example.a2048clone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.view.GestureDetector
import android.view.MotionEvent

// Debug tag
private const val TAG = "MainActivity"

// Constants to determine the sensitivity of swipes related to distance, speed, and x/y offset
private const val SWIPE_MIN_DISTANCE = 120
private const val SWIPE_MAX_OFF_PATH = 250
private const val SWIPE_THRESHOLD_VELOCITY = 200

class MainActivity : AppCompatActivity(),
                     GestureDetector.OnGestureListener,
                     GestureDetector.OnDoubleTapListener
{
    // Gesture Detector instance
    private lateinit var mDetector: GestureDetectorCompat

    // Game singleton instance
    val game = Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar
        supportActionBar?.hide()

        // Hide the UI
        hideUI()

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

    fun setTestText(string : String) { testTextView.text = string }

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
        when(getSlope(swipe1!!.x,swipe1!!.y,swipe2!!.x,swipe2!!.y))
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
