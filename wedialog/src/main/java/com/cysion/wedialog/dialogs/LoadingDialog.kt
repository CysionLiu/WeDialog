package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cysion.wedialog.R

class LoadingDialog : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.run {
            val builder = AlertDialog.Builder(activity)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.we_dialog_loading, null)
            val dialog = builder.create()
            val window = dialog.window
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(true)
            //摆脱token的限制，注意清单文件alert权限
            val p = window!!.attributes // 获取对话框当前的参数值
            window.decorView.setBackgroundColor(0X00000000)
            p.width = (resources.displayMetrics.widthPixels).toInt()
            window.attributes = p
            window.setDimAmount(0.3f)
            window.setBackgroundDrawable(null)
            dialog.show()
            dialog.setContentView(view)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)
    }
}