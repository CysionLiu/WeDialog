package com.cysion.wedialog

import android.view.Gravity
import androidx.fragment.app.FragmentActivity
import com.cysion.wedialog.dialogs.CustomDialog
import com.cysion.wedialog.dialogs.LoadingDialog
import com.cysion.wedialog.dialogs.NormalDialog

/*
* Manager for all real dialogs' invocation*/

object WeDialog {

    //global loading
    private var loadingDialog: LoadingDialog? = null
    //global config,can init once outside
    internal var weConfig = Config()

    private var hasInit = false

    fun initOnce(config: Config) {
        if (hasInit) {
            return
        }
        hasInit = true
        weConfig = config
    }

    /*
    * begin loading*/
    fun loading(activity: FragmentActivity) {
        //do nothing when loading
        loadingDialog?.run {
            dialog?.run {
                if (isShowing) {
                    return
                }
            }
        }
        if (activity.isFinishing) {
            return
        }
        loadingDialog = LoadingDialog()
        loadingDialog?.show(activity.supportFragmentManager, "")

    }

    /*
    * dismiss loading dialog*/
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

    fun custom(activity: FragmentActivity): CustomDialog.Builder {
        return CustomDialog.Builder(activity)
    }

    fun normal(activity: FragmentActivity): NormalDialog.Builder {
        return NormalDialog.Builder(activity)
    }

    class Config {
        //0-1f
        var mDimCount = 0.5f

        var mGravity = Gravity.CENTER

        //0-1f
        var mWidthRatio = 0.9f

        var mCancelable = true

        var mCancelableOutSide = false

        var mAnimStyle = 0
    }
}