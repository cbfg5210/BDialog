package com.dialog.demo.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dialog.BDialog
import com.dialog.demo.R
import kotlinx.android.synthetic.main.fragment_dialog.view.*

/**
 * 添加人：  Tom Hawk
 * 添加时间：2019/9/24 15:53
 * 功能描述：
 * <p>
 * 修改人：  Tom Hawk
 * 修改时间：2019/9/24 15:53
 * 修改内容：
 */
class DDialogFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_dialog, container, false)
        layout.btnDialog1.setOnClickListener(this)
        layout.btnDialog2.setOnClickListener(this)
        return layout
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnDialog1 -> {
                BDialog.get()
                    .init { _: BDialog, builder: AlertDialog.Builder ->
                        builder.setMessage("这是一个只有内容的 Dialog")
                    }
                    .show(lifecycle, childFragmentManager, "Dialog_1")
            }

            R.id.btnDialog2 -> {
                BDialog.get()
                    .init { _: BDialog, builder: AlertDialog.Builder ->
                        builder.setTitle("温馨提示")
                            .setMessage("这是一个带标题、内容、按钮的 Dialog")
                            .setPositiveButton("知道了", null)
                    }
                    .show(lifecycle, childFragmentManager, "Dialog_2")
            }
        }
    }
}