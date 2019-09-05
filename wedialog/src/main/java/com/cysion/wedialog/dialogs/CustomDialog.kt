package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cysion.wedialog.DParams
import com.cysion.wedialog.WeDialog
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
        val WE_KEY_V_Margin = "WE_KEY_V_Margin"
        val WE_KEY_H_Margin = "WE_KEY_H_Margin"
        val WE_KEY_ANIM = "WE_KEY_ANIM"
    }

    @LayoutRes
    private var mLayoutId: Int = 0

    private var dialogViewHandler: ViewHandler? = null

    private var mBundle: Bundle = Bundle()

    private var mDimCount = 0f

    private var mGravity = Gravity.CENTER

    private var mWidthRatio = 0.0f

    private var mCancelable = true

    private var mCancelableOutSide = false

    private var mVerMargin = 0f
    private var mHorMargin = 0f
    private var mWindowAnim = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getSavedData(savedInstanceState)
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
            p.height = WindowManager.LayoutParams.WRAP_CONTENT
            p.verticalMargin = mVerMargin
            p.horizontalMargin = mHorMargin
            window.attributes = p
            if (mWindowAnim != 0) {
                window.setWindowAnimations(mWindowAnim)
            }
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

    private fun getSavedData(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            dialogViewHandler = getSerializable(WE_KEY_LISTENER) as ViewHandler?
            mBundle.putAll(getBundle(WE_KEY_BUNDLE))
            mLayoutId = getInt(WE_KEY_LAYOUT)
            mWindowAnim = getInt(WE_KEY_ANIM)
            mDimCount = getFloat(WE_KEY_DIM)
            mVerMargin = getFloat(WE_KEY_V_Margin)
            mHorMargin = getFloat(WE_KEY_H_Margin)
            mGravity = getInt(WE_KEY_GRAVITY)
            mWidthRatio = getFloat(WE_KEY_WIDTH_RATIO)
            mCancelable = getBoolean(WE_KEY_CANCEL)
            mCancelableOutSide = getBoolean(WE_KEY_CANCEL_OUTSIDE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putSerializable(WE_KEY_LISTENER, dialogViewHandler)
            putBundle(WE_KEY_BUNDLE, mBundle)
            putInt(WE_KEY_LAYOUT, mLayoutId)
            putInt(WE_KEY_ANIM, mWindowAnim)
            putFloat(WE_KEY_DIM, mDimCount)
            putFloat(WE_KEY_V_Margin, mVerMargin)
            putFloat(WE_KEY_H_Margin, mHorMargin)
            putInt(WE_KEY_GRAVITY, mGravity)
            putFloat(WE_KEY_WIDTH_RATIO, mWidthRatio)
            putBoolean(WE_KEY_CANCEL, mCancelable)
            putBoolean(WE_KEY_CANCEL_OUTSIDE, mCancelableOutSide)
        }
    }


    class Builder(val activity: FragmentActivity) {

        private var bParams: DParams = DParams()
        private var bLayoutRes: Int = 0
        private var bAnim: Int = 0
        private var bVerMargin = 0f
        private var bHorMargin = 0f
        private var bDimCount = WeDialog.weConfig.mDimCount
        private var bGravity = WeDialog.weConfig.mGravity
        private var bWidthRatio = WeDialog.weConfig.mWidthRatio
        private var bCancelable = WeDialog.weConfig.mCancelable
        private var bCancelableOutSide = WeDialog.weConfig.mCancelableOutSide


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

        fun setAnim(@StyleRes styleRes: Int): Builder {
            bAnim = styleRes
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

        //set vertical margin ratio from -1f to 1f
        fun setVMargin(ratio: Float): Builder {
            bVerMargin = ratio
            return this
        }

        //setHorizontal margin ratio from -1f to 1f
        fun setHMargin(ratio: Float): Builder {
            bHorMargin = ratio
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
                mWindowAnim = bAnim
                mVerMargin = bVerMargin
                mHorMargin = bHorMargin
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