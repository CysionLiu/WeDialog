package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.cysion.wedialog.R
import com.cysion.wedialog.WeDialog

class LoadingDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.run {
            if (isFinishing) {
                return super.onCreateDialog(savedInstanceState)
            }
            val builder = AlertDialog.Builder(activity)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.we_dialog_loading, null)
            val dialog = builder.create()
            val window = dialog.window
            dialog.setCanceledOnTouchOutside(WeDialog.weConfig.mCancelableOutSide)
            setCancelable(WeDialog.weConfig.mCancelable)
            //摆脱token的限制，注意清单文件alert权限
            val p = window!!.attributes // 获取对话框当前的参数值
            window.decorView.setBackgroundColor(0X00000000)
            p.width = (resources.displayMetrics.widthPixels*WeDialog.weConfig.mWidthRatio).toInt()
            window.attributes = p
            window.setDimAmount(WeDialog.weConfig.mDimCount)
            window.setBackgroundDrawable(null)
            dialog.show()
            dialog.setContentView(view)
            dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }
}