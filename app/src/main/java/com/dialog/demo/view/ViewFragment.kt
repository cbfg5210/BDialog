package com.dialog.demo.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.dialog.BDialog
import com.dialog.demo.R
import kotlinx.android.synthetic.main.fragment_view.view.*
import kotlinx.android.synthetic.main.layout_dialog_1.view.tvMessage
import kotlinx.android.synthetic.main.layout_dialog_2.view.tvBtn
import kotlinx.android.synthetic.main.layout_dialog_2.view.tvTitle
import kotlinx.android.synthetic.main.layout_dialog_bottom.view.*
import kotlinx.android.synthetic.main.layout_dialog_input.view.*

/**
 * 添加人：  Tom Hawk
 * 添加时间：2019/9/24 15:53
 * 功能描述：
 * <p>
 * 修改人：  Tom Hawk
 * 修改时间：2019/9/24 15:53
 * 修改内容：
 */
class ViewFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_view, container, false)
        layout.btnDialog1.setOnClickListener(this)
        layout.btnDialog2.setOnClickListener(this)
        layout.btnDialogInput.setOnClickListener(this)
        layout.btnDialogBottom.setOnClickListener(this)
        return layout
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnDialog1 -> {
                BDialog.get()
                        .init(R.layout.layout_dialog_1) { _, view -> view.tvMessage.text = "这是一个只有内容的 Dialog" }
                        .show(lifecycle, childFragmentManager, "ViewDialog_1")
            }

            R.id.btnDialog2 -> {
                BDialog.get()
                        .init(R.layout.layout_dialog_2) { dialog, view ->
                            view.tvTitle.text = "温馨提示"
                            view.tvMessage.text = "这是一个带标题、内容、按钮的 Dialog"
                            view.tvBtn.text = "知道了"
                            view.tvBtn.setOnClickListener { dialog.dismiss(lifecycle) }
                        }
                        .show(lifecycle, childFragmentManager, "ViewDialog_2")
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
                                    Toast.makeText(context, "你没有许下愿望喔~", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "你的愿望已被接收: $text", Toast.LENGTH_LONG).show()
                                }
                                dialog.dismiss(lifecycle)
                            }
                        }
                        .show(lifecycle, childFragmentManager, "ViewDialog_Input")
            }

            R.id.btnDialogBottom -> {
                BDialog.get()
                        .apply { setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BLayoutDialogSlideInBottomTheme) }
                        .init(R.layout.layout_dialog_bottom) { _, view ->
                            view.tvOption1.setOnClickListener { Toast.makeText(context, "Click option1", Toast.LENGTH_SHORT).show() }
                            view.tvOption2.setOnClickListener { Toast.makeText(context, "Click option2", Toast.LENGTH_SHORT).show() }
                            view.tvOption3.setOnClickListener { Toast.makeText(context, "Click option3", Toast.LENGTH_SHORT).show() }
                        }
                        .show(lifecycle, childFragmentManager, "ViewDialog_Bottom")
            }
        }
    }
}