package com.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle

/**
 * 添加人：  Tom Hawk
 * 添加时间：2019/2/19 16:58
 * 功能描述：
 *
 *
 * 修改人：  Tom Hawk
 * 修改时间：2019/2/19 16:58
 * 修改内容：
 */
class BDialog : DialogFragment() {
    @Type
    private var type: Int = 0
    @LayoutRes
    private var layoutRes: Int = 0
    private var isFullScreen: Boolean = false

    private val builder: AlertDialog.Builder by lazy { AlertDialog.Builder(context!!) }
    private val args: Bundle by lazy { Bundle() }

    private lateinit var initViewListener: ((dialog: BDialog, view: View) -> Unit)
    private lateinit var initDialogListener: ((dialog: BDialog, builder: AlertDialog.Builder) -> Unit)
    private var dismissListener: DialogInterface.OnDismissListener? = null

    @IntDef(Type.Dialog, Type.View)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Type {
        companion object {
            const val Dialog = 1
            const val View = 2
        }
    }

    fun args(): Bundle {
        return args
    }

    fun setFullScreen(isFullScreen: Boolean): BDialog {
        this.isFullScreen = isFullScreen
        return this
    }

    fun init(layoutRes: Int, initViewListener: (dialog: BDialog, view: View) -> Unit): BDialog {
        this.type = Type.View
        this.layoutRes = layoutRes
        this.initViewListener = initViewListener
        return this
    }

    fun init(initDialogListener: (dialog: BDialog, builder: AlertDialog.Builder) -> Unit): BDialog {
        this.type = Type.Dialog
        this.initDialogListener = initDialogListener
        return this
    }

    fun setDismissListener(dismissListener: DialogInterface.OnDismissListener?): BDialog {
        this.dismissListener = dismissListener
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (type == Type.View) {
            return super.onCreateDialog(savedInstanceState)
        }
        initDialogListener.invoke(this, builder)
        return builder.create()
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        if (type == Type.Dialog) {
            return super.onGetLayoutInflater(savedInstanceState)
        }

        //如果有传入theme则使用传入的，没有传入则使用默认的
        setStyle(STYLE_NORMAL, if (theme == 0) R.style.BLayoutDialogTheme else theme)
        var layoutInflater = super.onGetLayoutInflater(savedInstanceState)
        val aty = activity ?: return layoutInflater

        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        layoutInflater = aty.layoutInflater

        val mDialog = dialog ?: return layoutInflater
        val mWindow = mDialog.window ?: return layoutInflater

        if (!mWindow.isFloating) {
            setupDialog(mDialog, mWindow)
            layoutInflater = BDialogLayoutInflater(requireContext(), layoutInflater) {
                if (isCancelable) {
                    dismiss()
                }
            }
        }

        return layoutInflater
    }

    private fun setupDialog(dialog: Dialog, window: Window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        if (isFullScreen) {
            //隐藏状态栏、底部栏,弹出dialog时会出现一下而后才隐藏
            //ScreenUtils.hideBottomUIMenu(this)
            //弹出dialog时,保持隐藏状态栏、底部栏
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        }

        dialog.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                if (isCancelable) {
                    dismiss()
                }
                true
            } else {
                false
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (type == Type.Dialog || layoutRes == 0) {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        val layoutView = inflater.inflate(layoutRes, null)
        initViewListener.invoke(this, layoutView)
        return layoutView
    }

    fun show(lifecycle: Lifecycle, manager: FragmentManager?, tag: String?) {
        manager ?: return
        if (isActive(lifecycle)) {
            val bDialog = manager.findFragmentByTag(tag)
            if (bDialog == null || !bDialog.isAdded) {
                show(manager, tag)
            }
        }
    }

    fun show(lifecycle: Lifecycle, transaction: FragmentTransaction, tag: String?): Int {
        return if (isActive(lifecycle) && !isAdded) show(transaction, tag) else 0
    }

    fun dismiss(lifecycle: Lifecycle) {
        if (isAdded && isActive(lifecycle)) {
            dismiss()
        }
    }

    /**
     * 判断Lifecycle是否处于活跃状态
     */
    private fun isActive(lifecycle: Lifecycle): Boolean {
        val state = lifecycle.currentState
        return state == Lifecycle.State.INITIALIZED || state.isAtLeast(Lifecycle.State.STARTED)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onDismiss(dialog)
    }

    companion object {
        fun get(): BDialog {
            return BDialog()
        }
    }
}