package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.cysion.wedialog.R
import com.cysion.wedialog.WeDialog

class LoadingDialog : DialogFragment() {
    private lateinit var text: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.run {
            if (isFinishing) {
                return super.onCreateDialog(savedInstanceState)
            }
            val builder = AlertDialog.Builder(activity, R.style.we_dialog_default_style)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.we_dialog_loading, null)
            val tvMsg = view.findViewById<TextView>(R.id.we_tv_loading_msg)
            if (TextUtils.isEmpty(text)) {
                tvMsg.visibility = View.GONE
            } else {
                tvMsg.visibility = View.VISIBLE
                tvMsg.text = text
            }
            val dialog = builder.create()
            dialog.show()
            val window = dialog.window
            dialog.setCanceledOnTouchOutside(WeDialog.weConfig.mCancelableOutSide)
            setCancelable(WeDialog.weConfig.mCancelable)
            val p = window!!.attributes
            window.decorView.setBackgroundColor(0X00000000)
            p.width = (resources.displayMetrics.widthPixels * WeDialog.weConfig.mWidthRatio).toInt()
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
        WeDialog.dismiss()
    }

    companion object {
        fun create(text: String): LoadingDialog {
            val dialog = LoadingDialog()
            dialog.text = text;
            return dialog
        }
    }
}