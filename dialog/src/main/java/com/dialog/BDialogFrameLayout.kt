package com.dialog

import android.content.Context
import android.graphics.Rect
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout

class BDialogFrameLayout(context: Context) : FrameLayout(context) {
    internal var onTouchOutsideListener: OnTouchOutsideListener? = null
    private val gestureDetector: GestureDetector

    fun setOnTouchOutsideListener(onTouchOutsideListener: OnTouchOutsideListener) {
        this.onTouchOutsideListener = onTouchOutsideListener
    }

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val rect = Rect()
                getHitRect(rect)
                val count = childCount
                for (i in count - 1 downTo -1 + 1) {
                    val child = getChildAt(i)
                    val outRect = Rect()
                    child.getHitRect(outRect)
                    if (outRect.contains(e.x.toInt(), e.y.toInt())) {
                        return false
                    }
                }

                onTouchOutsideListener?.onTouchOutside()

                return true
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    interface OnTouchOutsideListener {
        fun onTouchOutside()
    }
}