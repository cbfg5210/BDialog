package com.dialog.demo

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.dialog.BDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_dialog_1.view.tvMessage
import kotlinx.android.synthetic.main.layout_dialog_2.view.tvBtn
import kotlinx.android.synthetic.main.layout_dialog_2.view.tvTitle
import kotlinx.android.synthetic.main.layout_dialog_bottom.view.*
import kotlinx.android.synthetic.main.layout_dialog_input.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDialog1.setOnClickListener(this)
        btnDialog2.setOnClickListener(this)
        btnVDialog1.setOnClickListener(this)
        btnVDialog2.setOnClickListener(this)
        btnDialogInput.setOnClickListener(this)
        btnDialogBottom.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnDialog1 -> {
                BDialog.get()
                        .init { _: BDialog, builder: AlertDialog.Builder -> builder.setMessage("这是一个只有内容的 Dialog") }
                        .show(lifecycle, supportFragmentManager, "Dialog_1")
            }

            R.id.btnDialog2 -> {
                BDialog.get()
                        .init { _: BDialog, builder: AlertDialog.Builder ->
                            builder.setTitle("温馨提示")
                                    .setMessage("这是一个带标题、内容、按钮的 Dialog")
                                    .setPositiveButton("知道了", null)
                        }
                        .show(lifecycle, supportFragmentManager, "Dialog_2")
            }

            R.id.btnVDialog1 -> {
                BDialog.get()
                        .init(R.layout.layout_dialog_1) { _, view -> view.tvMessage.text = "这是一个只有内容的 Dialog" }
                        .show(lifecycle, supportFragmentManager, "ViewDialog_1")
            }

            R.id.btnVDialog2 -> {
                BDialog.get()
                        .init(R.layout.layout_dialog_2) { dialog, view ->
                            view.tvTitle.text = "温馨提示"
                            view.tvMessage.text = "这是一个带标题、内容、按钮的 Dialog"
                            view.tvBtn.text = "知道了"
                            view.tvBtn.setOnClickListener { dialog.dismiss(lifecycle) }
                        }
                        .show(lifecycle, supportFragmentManager, "ViewDialog_2")
            }

            R.id.btnDialogInput -> {
                BDialog.get()
                        //通过 Bundle 传入外部数据,在 init 中取出使用
                        .apply { args().putString("default", "找一个女票...") }
                        .init(R.layout.layout_dialog_input) { dialog, view ->
                            view.tvTitle.text = "许下你的愿望吧:"
                            view.tvBtn.text = "许好了"

                            //从 Bundle 中获取数据使用
                            view.etMessage.hint = dialog.args().getString("default")

                            view.tvBtn.setOnClickListener {
                                val text = view.etMessage.text
                                if (TextUtils.isEmpty(text)) {
                                    Toast.makeText(this, "你没有许下愿望喔~", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this, "你的愿望已被接收: $text", Toast.LENGTH_LONG).show()
                                }
                                dialog.dismiss(lifecycle)
                            }
                        }
                        .show(lifecycle, supportFragmentManager, "ViewDialog_Input")
            }

            R.id.btnDialogBottom -> {
                BDialog.get()
                        .apply { setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BLayoutDialogSlideInBottomTheme) }
                        .init(R.layout.layout_dialog_bottom) { _, view ->
                            view.tvOption1.setOnClickListener { Toast.makeText(this, "Click option1", Toast.LENGTH_SHORT).show() }
                            view.tvOption2.setOnClickListener { Toast.makeText(this, "Click option2", Toast.LENGTH_SHORT).show() }
                            view.tvOption3.setOnClickListener { Toast.makeText(this, "Click option3", Toast.LENGTH_SHORT).show() }
                        }
                        .show(lifecycle, supportFragmentManager, "ViewDialog_Bottom")
            }
        }
    }
}
