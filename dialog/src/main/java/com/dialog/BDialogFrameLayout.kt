package com.dialog

import android.content.Context
import android.graphics.Rect
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout

class BDialogFrameLayout(context: Context) : FrameLayout(context) {
    private var onTouchOutsideListener: (() -> Unit)? = null

    private val gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean = true

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val rect = Rect()
            getHitRect(rect)

            for (i in childCount - 1 downTo -1 + 1) {
                val child = getChildAt(i)
                val outRect = Rect()
                child.getHitRect(outRect)
                if (outRect.contains(e.x.toInt(), e.y.toInt())) {
                    return false
                }
            }

            onTouchOutsideListener?.invoke()

            return true
        }
    })

    fun setOnTouchOutsideListener(onTouchOutsideListener: () -> Unit) {
        this.onTouchOutsideListener = onTouchOutsideListener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean = gestureDetector.onTouchEvent(event)
}