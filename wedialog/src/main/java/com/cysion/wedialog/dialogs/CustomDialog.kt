package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cysion.wedialog.DParams
import com.cysion.wedialog.listener.ViewHandler

class CustomDialog : DialogFragment() {
    companion object {
        val WE_KEY_LISTENER = "WE_KEY_LISTENER"
        val WE_KEY_BUNDLE = "WE_KEY_BUNDLE"
        val WE_KEY_LAYOUT = "WE_KEY_LAYOUT"
        val WE_KEY_DIM = "WE_KEY_DIM"
        val WE_KEY_GRAVITY = "WE_KEY_GRAVITY"
        val WE_KEY_WIDTH_RATIO = "WE_KEY_WIDTH_RATIO"
        val WE_KEY_CANCEL = "WE_KEY_CANCEL"
        val WE_KEY_CANCEL_OUTSIDE = "WE_KEY_CANCEL_OUTSIDE"
        val WE_KEY_TAG = "WE_KEY_TAG"
    }

    @LayoutRes
    private var mLayoutId: Int = 0

    private var dialogViewHandler: ViewHandler? = null

    private var mBundle: Bundle = Bundle()

    private var mDimCount = 0.8f

    private var mGravity = Gravity.CENTER

    private var mWidthRatio = 0.9f

    private var mCancelable = true

    private var mCancelableOutSide = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        savedInstanceState?.run {
            dialogViewHandler = getSerializable(WE_KEY_LISTENER) as ViewHandler?
            mBundle.putAll(getBundle(WE_KEY_BUNDLE))
            mLayoutId = getInt(WE_KEY_LAYOUT)
            mDimCount = getFloat(WE_KEY_DIM)
            mGravity = getInt(WE_KEY_GRAVITY)
            mWidthRatio = getFloat(WE_KEY_WIDTH_RATIO)
            mCancelable = getBoolean(WE_KEY_CANCEL)
            mCancelableOutSide = getBoolean(WE_KEY_CANCEL_OUTSIDE)
        }
        activity?.run {
            val builder = AlertDialog.Builder(activity)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(mLayoutId, null)
            val dialog = builder.create()
            val window = dialog.window
            //摆脱token的限制，注意清单文件alert权限
            val p = window!!.attributes // 获取对话框当前的参数值
            window.decorView.setBackgroundColor(0X00000000)
            window.setBackgroundDrawable(null)
            window.setGravity(mGravity)
            p.width = (resources.displayMetrics.widthPixels * mWidthRatio).toInt()
            window.attributes = p
            window.setDimAmount(mDimCount)
            dialog.show()
            dialog.setCanceledOnTouchOutside(mCancelableOutSide)
            setCancelable(mCancelable)
            dialogViewHandler?.handle(this@CustomDialog, view, mBundle)
            dialog.setContentView(view)
            dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putSerializable(WE_KEY_LISTENER, dialogViewHandler)
            putBundle(WE_KEY_BUNDLE, mBundle)
            putInt(WE_KEY_LAYOUT, mLayoutId)
            putFloat(WE_KEY_DIM, mDimCount)
            putInt(WE_KEY_GRAVITY, mGravity)
            putFloat(WE_KEY_WIDTH_RATIO, mWidthRatio)
            putBoolean(WE_KEY_CANCEL, mCancelable)
            putBoolean(WE_KEY_CANCEL_OUTSIDE, mCancelableOutSide)
        }
    }



    class Builder(val activity: FragmentActivity) {

        private var bParams: DParams = DParams()
        private var bLayoutRes: Int = 0
        private var bDimCount = 0.8f
        private var bGravity = Gravity.CENTER
        private var bWidthRatio = 0.9f
        private var bCancelable = true
        private var bCancelableOutSide = false

        fun params(params: DParams): Builder {
            bParams = params
            return this
        }

        fun layout(@LayoutRes layoutId: Int): Builder {
            bLayoutRes = layoutId
            return this
        }

        fun setGravity(gravity: Int): Builder {
            bGravity = gravity
            return this
        }

        fun setDim(dim: Float): Builder {
            bDimCount = dim
            return this
        }

        fun setWidthRatio(ratio: Float): Builder {
            bWidthRatio = ratio
            return this
        }

        fun setCancelable(c: Boolean): Builder {
            bCancelable = c
            return this
        }

        fun setCancelableOutSide(c: Boolean): Builder {
            bCancelableOutSide = c
            return this
        }

        fun show(callback: ViewHandler) {
            if (bLayoutRes == 0) {
                throw IllegalArgumentException("wrong layoutId")
            }
            val dialog = CustomDialog()
            dialog.run {
                mBundle = bParams.bundle
                mLayoutId = bLayoutRes
                dialogViewHandler = callback
                mDimCount = bDimCount
                mWidthRatio = bWidthRatio
                mGravity = bGravity
                mCancelableOutSide = bCancelableOutSide
                mCancelable = bCancelable
            }
            dialog.show(activity.supportFragmentManager, "")
        }
    }
}