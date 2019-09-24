package com.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BDialogLayoutInflater(context: Context, private val layoutInflater: LayoutInflater, private val listener: () -> Unit) : LayoutInflater(context) {

    override fun cloneInContext(context: Context): LayoutInflater {
        return layoutInflater.cloneInContext(context)
    }

    override fun inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View {
        val frameLayout = BDialogFrameLayout(context)
        frameLayout.setOnTouchOutsideListener(listener)
        frameLayout.layoutParams = ViewGroup.LayoutParams(-1, -1)
        layoutInflater.inflate(resource, frameLayout, true)
        return frameLayout
    }
}