package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.cysion.wedialog.WeDialog
import com.cysion.wedialog.WeParams
import com.cysion.wedialog.listener.ListenerHolder
import com.cysion.wedialog.listener.OnViewHandler


class CustomDialog : DialogFragment() {
    companion object {
        val WE_KEY_EVENT_HOLDER = "WE_KEY_EVENT_HOLDER"
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

    private var mListenerHolder: ListenerHolder? = null

    private var mBundle: Bundle = Bundle()

    private var mDimCount = 0f

    private var mGravity = Gravity.CENTER

    private var mWidthRatio = 0.0f

    private var mCancelable = true

    private var mCancelableOutSide = false

    private var mYOffset = 0
    private var mXOffset = 0
    private var mWindowAnim = 0
    private var mAnchor: View? = null
    private var mSaveState: Bundle? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getSavedData(savedInstanceState)
        activity?.run {
            if (isFinishing) {
                return super.onCreateDialog(savedInstanceState)
            }
            val builder = AlertDialog.Builder(activity)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(mLayoutId, null)
            val dialog = builder.create()
            dialog.show()
            val window = dialog.window
            val p = window!!.attributes
            window.decorView.setBackgroundColor(0X00000000)
            window.setBackgroundDrawable(null)
            window.setGravity(mGravity)
            p.width = (resources.displayMetrics.widthPixels * mWidthRatio).toInt()
            p.height = WindowManager.LayoutParams.WRAP_CONTENT

            val sw = resources.displayMetrics.widthPixels
            val realHeight = getRealScreenHeight()
            val disHeight = resources.displayMetrics.heightPixels
            val sta = getStatusBarHeight()
            val nav = getNavigationBarHeight()
            var sh = realHeight
            if (realHeight.equals(disHeight + sta)) {
                sh = realHeight
            } else if (realHeight.equals(disHeight + nav)) {
                sh = disHeight
            } else if (realHeight.equals(disHeight + nav + sta)) {
                sh = realHeight - nav
            }else if(Settings.Global.getInt(getContentResolver(), "force_fsg_nav_bar", 0) == 0){
                //虚拟键展示+realheight，displayheight没有明确关系；
                sh = realHeight-nav
            }
            //set anchor
            mAnchor?.run {
                val ord = IntArray(2)
                getLocationInWindow(ord)
                if ((ord[0] + width / 2) <= sw / 2) {
                    if (ord[1] + height / 2 < sh / 2) {
                        window.setGravity(Gravity.TOP)
                        //left-top
                        val xdelta = ord[0] + width - (sw / 2 - p.width / 2)
                        val ydelta = ord[1] + height - getStatusBarHeight()
                        p.y = p.y + ydelta
                        p.x = p.x + xdelta
                    } else {
                        //left-bottom
                        window.setGravity(Gravity.BOTTOM)
                        val xdelta = ord[0] + width - (sw / 2 - p.width / 2)
                        val ydelta = sh - ord[1]
                        p.y = p.y + ydelta
                        p.x = p.x + xdelta
                    }
                } else {
                    if (ord[1] + height / 2 < sh / 2) {
                        window.setGravity(Gravity.TOP)
                        //right-top
                        val xdelta = ord[0] - (sw / 2 + p.width / 2)
                        val ydelta = ord[1] + height - getStatusBarHeight()
                        p.y = p.y + ydelta
                        p.x = p.x + xdelta
                    } else {
                        //right-bottom
                        window.setGravity(Gravity.BOTTOM)
                        val xdelta = ord[0] - (sw / 2 + p.width / 2)
                        val ydelta = sh - ord[1]
                        p.y = p.y + ydelta
                        p.x = p.x + xdelta
                    }
                }
            }
            p.y = p.y + mYOffset
            p.x = p.x + mXOffset
            window.attributes = p
            if (WeDialog.weConfig.mAnimStyle != 0) {
                window.setWindowAnimations(WeDialog.weConfig.mAnimStyle)
            }
            if (mWindowAnim != 0) {
                window.setWindowAnimations(mWindowAnim)
            }
            window.setDimAmount(mDimCount)
            dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            dialog.setCanceledOnTouchOutside(mCancelableOutSide)
            setCancelable(mCancelable)
            mListenerHolder?.aViewHandler?.invoke(this@CustomDialog, view, mBundle)
            dialog.setContentView(view)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)

    }

    private fun getSavedData(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            mListenerHolder ?: run {
                mListenerHolder = getSerializable(WE_KEY_EVENT_HOLDER) as ListenerHolder?
            }
            mBundle.putAll(getBundle(WE_KEY_BUNDLE))
            mLayoutId = getInt(WE_KEY_LAYOUT)
            mWindowAnim = getInt(WE_KEY_ANIM)
            mDimCount = getFloat(WE_KEY_DIM)
            mYOffset = getInt(WE_KEY_V_Margin)
            mXOffset = getInt(WE_KEY_H_Margin)
            mGravity = getInt(WE_KEY_GRAVITY)
            mWidthRatio = getFloat(WE_KEY_WIDTH_RATIO)
            mCancelable = getBoolean(WE_KEY_CANCEL)
            mCancelableOutSide = getBoolean(WE_KEY_CANCEL_OUTSIDE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mSaveState = outState
        outState.run {
            putBundle(WE_KEY_BUNDLE, mBundle)
            putInt(WE_KEY_LAYOUT, mLayoutId)
            putInt(WE_KEY_ANIM, mWindowAnim)
            putFloat(WE_KEY_DIM, mDimCount)
            putInt(WE_KEY_V_Margin, mYOffset)
            putInt(WE_KEY_H_Margin, mXOffset)
            putInt(WE_KEY_GRAVITY, mGravity)
            putFloat(WE_KEY_WIDTH_RATIO, mWidthRatio)
            putBoolean(WE_KEY_CANCEL, mCancelable)
            putBoolean(WE_KEY_CANCEL_OUTSIDE, mCancelableOutSide)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mSaveState?.putSerializable(WE_KEY_EVENT_HOLDER, mListenerHolder)

    }

    fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId == 0) {
            return 20 * resources.displayMetrics.density.toInt()
        }
        return resources.getDimensionPixelSize(resourceId)
    }

    //get bottom nav height
    fun getNavigationBarHeight(): Int {
        var result = 0
        val res = resources
        val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun getRealScreenHeight(): Int {
        activity?.run {
            var dpi = 0
            val display = window.windowManager.getDefaultDisplay()
            val dm = DisplayMetrics()
            val c: Class<*>
            try {
                c = Class.forName("android.view.Display")
                val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
                method.invoke(display, dm)
                dpi = dm.heightPixels
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return dpi
        }
        return resources.displayMetrics.heightPixels
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

    class Builder(val activity: FragmentActivity) {

        private var bParams: WeParams = WeParams()
        private var bLayoutRes: Int = 0
        private var bAnim: Int = 0
        private var bYOffset = 0
        private var bXOffset = 0
        private var bDimCount = WeDialog.weConfig.mDimCount
        private var bGravity = WeDialog.weConfig.mGravity
        private var bWidthRatio = WeDialog.weConfig.mWidthRatio
        private var bCancelable = WeDialog.weConfig.mCancelable
        private var bCancelableOutSide = WeDialog.weConfig.mCancelableOutSide
        private var bAnchorView: View? = null


        fun params(params: WeParams): Builder {
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
        fun setYOffset(offset: Int): Builder {
            bYOffset = offset
            return this
        }

        fun anchor(anchor: View): Builder {
            bAnchorView = anchor
            return this
        }

        //setHorizontal margin ratio from -1f to 1f
        fun setXOffset(offset: Int): Builder {
            bXOffset = offset
            return this
        }

        fun show() {
            show { df, dialogView, bundle ->

            }
        }

        fun show(viewHandler: OnViewHandler) {
            if (bLayoutRes == 0) {
                throw IllegalArgumentException("wrong layoutId")
            }
            val dialog = CustomDialog()
            dialog.run {
                mBundle = bParams.bundle
                mLayoutId = bLayoutRes
                mWindowAnim = bAnim
                mYOffset = bYOffset
                mXOffset = bXOffset
                mDimCount = bDimCount
                mWidthRatio = bWidthRatio
                mGravity = bGravity
                mCancelableOutSide = bCancelableOutSide
                mCancelable = bCancelable
                mAnchor = bAnchorView
                mListenerHolder = ListenerHolder(aViewHandler = viewHandler)
            }
            dialog.show(activity.supportFragmentManager, "")
        }
    }
}