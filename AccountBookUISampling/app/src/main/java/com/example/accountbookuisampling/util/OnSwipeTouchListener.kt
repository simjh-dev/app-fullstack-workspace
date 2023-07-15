package com.example.accountbookuisampling.util

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.Exception
import kotlin.math.abs

internal open class OnSwipeTouchListener(context: Context?) : View.OnTouchListener {

    val gestureDetector: GestureDetector
    private val TAG = this::class.java.simpleName

    companion object {
        private const val SWIPE_DISTANCE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    open fun onSwipeRightToLeft() {
//        Log.e(TAG, "onSwipeRightToLeft")
    }

    open fun onSwipeLeftToRight() {
//        Log.e(TAG, "onSwipeLeftToRight")
    }

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val distanceX = e2.x - e1.x
            val distanceY = e2.y - e1.y
            if (abs(distanceX) > abs(distanceY) && abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && abs(
                    velocityX
                ) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (distanceX > 0) onSwipeLeftToRight() else onSwipeRightToLeft()
                return true
            }
            return false
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return try {
            gestureDetector.onTouchEvent(event)
        } catch(e: Exception) {
            true
        }
    }
}