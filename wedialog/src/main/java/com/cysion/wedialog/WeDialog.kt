package com.cysion.wedialog

import androidx.fragment.app.FragmentActivity
import com.cysion.wedialog.dialogs.CustomDialog
import com.cysion.wedialog.dialogs.LoadingDialog

class WeDialog {





    /*
    全局loading框
     */
    companion object {
        private var loadingDialog: LoadingDialog? = null



        fun loading(activity: FragmentActivity) {
            //在加载中，则不操作
            loadingDialog?.run {
                dialog?.run {
                    if (isShowing) {
                        return
                    }
                }
            }
            if (activity == null || activity.isFinishing) {
                return
            }
            loadingDialog = LoadingDialog()
            loadingDialog?.show(activity.supportFragmentManager, "")

        }

        fun dismiss() {
            loadingDialog?.run {
                dialog?.run {
                    if (isShowing) {
                        loadingDialog?.dismissAllowingStateLoss()
                    }
                }
                loadingDialog = null
            }
        }

         fun custom(activity: FragmentActivity):CustomDialog.Builder{
            return CustomDialog.Builder(activity)
        }
    }
}