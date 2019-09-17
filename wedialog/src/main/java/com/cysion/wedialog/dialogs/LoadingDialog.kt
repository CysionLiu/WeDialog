package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
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
            dialog.show()
            val window = dialog.window
            dialog.setCanceledOnTouchOutside(WeDialog.weConfig.mCancelableOutSide)
            setCancelable(WeDialog.weConfig.mCancelable)
            val p = window!!.attributes
            window.decorView.setBackgroundColor(0X00000000)
            p.width = (resources.displayMetrics.widthPixels*WeDialog.weConfig.mWidthRatio).toInt()
            window.attributes = p
            window.setDimAmount(WeDialog.weConfig.mDimCount)
            window.setBackgroundDrawable(null)
            window.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(view)
            dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: java.lang.Exception) {
            val ft = manager?.beginTransaction()
            ft?.add(this, tag)
            ft?.commitAllowingStateLoss()
        }
    }

    override fun onDetach() {
        super.onDetach()
        WeDialog.loadingDialog?.dismissAllowingStateLoss()
    }
}