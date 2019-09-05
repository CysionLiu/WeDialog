package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cysion.wedialog.R
import com.cysion.wedialog.WeDialog
import com.cysion.wedialog.listener.NoHandler
import com.cysion.wedialog.listener.YesHandler
import kotlinx.android.synthetic.main.we_dialog_normal.view.*

class NormalDialog : DialogFragment() {

    companion object {
        val WE_KEY_YES_LISTENER = "WE_KEY_YES_LISTENER"
        val WE_KEY_NO_LISTENER = "WE_KEY_NO_LISTENER"
        val WE_KEY_WIDTH_RATIO = "WE_KEY_WIDTH_RATIO"
        val WE_KEY_CANCEL = "WE_KEY_CANCEL"
        val WE_KEY_CANCEL_OUTSIDE = "WE_KEY_CANCEL_OUTSIDE"
        val WE_KEY_ANIM = "WE_KEY_ANIM"
        //----
        val WE_KEY_TITLE = "WE_KEY_TITLE"
        val WE_KEY_TITLE_SIZE = "WE_KEY_TITLE_SIZE"
        val WE_KEY_TITLE_COLOR = "WE_KEY_TITLE_COLOR"
        val WE_KEY_TEXT = "WE_KEY_TEXT"
        val WE_KEY_YES_TEXT = "WE_KEY_YES_TEXT"
        val WE_KEY_YES_COLOR = "WE_KEY_YES_COLOR"
        val WE_KEY_NO_TEXT = "WE_KEY_NO_TEXT"
        val WE_KEY_SHOW_ONE = "WE_KEY_SHOW_ONE"
    }

    private var mWidthRatio = 0.0f
    private var mCancelable = true
    private var mCancelableOutSide = false
    private var mWindowAnim = 0
    private var mYesHandler: YesHandler? = null
    private var mNoHandler: NoHandler? = null

    private var mTitle: String? = null
    //start with "#"
    private var mTitleColor: String? = null
    private var mTitleSize: Int = 0
    private var mText: String? = null
    private var mYesText: String? = null
    //start with "#"
    private var mYesColor: String? = null
    private var mNoText: String? = null
    private var mShowOneBtn = false


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getSavedData(savedInstanceState)
        activity?.run {
            val builder = AlertDialog.Builder(activity)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.we_dialog_normal, null)
            initViewAndEvent(view)
            val dialog = builder.create()
            val window = dialog.window
            //摆脱token的限制，注意清单文件alert权限
            val p = window!!.attributes // 获取对话框当前的参数值
            window.decorView.setBackgroundColor(0X00000000)
            window.setBackgroundDrawable(null)
            window.setGravity(Gravity.CENTER)
            p.width = (resources.displayMetrics.widthPixels * mWidthRatio).toInt()
            p.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = p
            if (mWindowAnim != 0) {
                window.setWindowAnimations(mWindowAnim)
            }
            window.setDimAmount(WeDialog.weConfig.mDimCount)
            dialog.show()
            dialog.setCanceledOnTouchOutside(mCancelableOutSide)
            setCancelable(mCancelable)
            dialog.setContentView(view)
            dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)

    }

    private fun initViewAndEvent(view: View?) {
        view?.run {
            if (TextUtils.isEmpty(mTitle)) {
                we_tv_title.visibility = View.GONE
            } else {
                we_tv_title.text = mTitle
            }
            if (TextUtils.isEmpty(mText)) {
                we_tv_text.visibility = View.GONE
            } else {
                we_tv_text.text = mText
            }
            if (!TextUtils.isEmpty(mYesText)) {
                we_btn_positive.text = mYesText
            }
            if (!TextUtils.isEmpty(mNoText)) {
                we_btn_negative.text = mNoText
            }

            if (!TextUtils.isEmpty(mTitleColor) && mTitleColor?.startsWith("#")!!) {
                we_tv_title.setTextColor(Color.parseColor(mTitleColor))
            }

            if (!TextUtils.isEmpty(mYesColor) && mYesColor?.startsWith("#")!!) {
                we_btn_positive.setTextColor(Color.parseColor(mYesColor))
            }
            if (mTitleSize > 0) {
                we_tv_title.setTextSize(mTitleSize.toFloat())
            }
            we_btn_negative.visibility = if (mShowOneBtn) View.GONE else View.VISIBLE
            we_btn_positive.setOnClickListener {
                mYesHandler?.onConfirm(this@NormalDialog)
            }
            we_btn_negative.setOnClickListener {
                dismissAllowingStateLoss()
                mNoHandler?.onCancel(this@NormalDialog)
            }

        }
    }

    private fun getSavedData(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            mNoHandler = getSerializable(WE_KEY_NO_LISTENER) as NoHandler?
            mYesHandler = getSerializable(WE_KEY_YES_LISTENER) as YesHandler?
            mWindowAnim = getInt(WE_KEY_ANIM)
            mWidthRatio = getFloat(WE_KEY_WIDTH_RATIO)
            mCancelable = getBoolean(WE_KEY_CANCEL)
            mCancelableOutSide = getBoolean(WE_KEY_CANCEL_OUTSIDE)

            mTitle = getString(WE_KEY_TITLE)
            mTitleColor = getString(WE_KEY_TITLE_COLOR)
            mTitleSize = getInt(WE_KEY_TITLE_SIZE)
            mText = getString(WE_KEY_TEXT)
            mYesText = getString(WE_KEY_YES_TEXT)
            mYesColor = getString(WE_KEY_YES_COLOR)
            mNoText = getString(WE_KEY_NO_TEXT)
            mShowOneBtn = getBoolean(WE_KEY_SHOW_ONE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putSerializable(WE_KEY_NO_LISTENER, mNoHandler)
            putSerializable(WE_KEY_YES_LISTENER, mYesHandler)
            putInt(WE_KEY_ANIM, mWindowAnim)
            putFloat(WE_KEY_WIDTH_RATIO, mWidthRatio)
            putBoolean(WE_KEY_CANCEL, mCancelable)
            putBoolean(WE_KEY_CANCEL_OUTSIDE, mCancelableOutSide)

            putString(WE_KEY_TITLE, mTitle)
            putString(WE_KEY_TITLE_COLOR, mTitleColor)
            putInt(WE_KEY_TITLE_SIZE, mTitleSize)
            putString(WE_KEY_TEXT, mText)
            putString(WE_KEY_YES_TEXT, mYesText)
            putString(WE_KEY_YES_COLOR, mYesColor)
            putString(WE_KEY_NO_TEXT, mNoText)
            putBoolean(WE_KEY_SHOW_ONE, mShowOneBtn)

        }
    }


    class Builder(val activity: FragmentActivity) {

        private var bAnim: Int = 0
        private var bDimCount = WeDialog.weConfig.mDimCount
        private var bWidthRatio = WeDialog.weConfig.mWidthRatio
        private var bCancelable = WeDialog.weConfig.mCancelable
        private var bCancelableOutSide = WeDialog.weConfig.mCancelableOutSide
        private var bYesHandler: YesHandler? = null
        private var bNoHandler: NoHandler? = null
        private var bTitle: String? = null
        //start with "#"
        private var bTitleColor: String? = null
        private var bTitleSize: Int = 0
        private var bText: String? = null
        private var bYesText: String? = null
        //start with "#"
        private var bYesColor: String? = null
        private var bNoText: String? = null
        private var bShowOneBtn = false

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

        fun title(title: String): Builder {
            bTitle = title
            return this
        }

        fun titleColor(color: String): Builder {
            bTitleColor = color
            return this
        }

        fun titleSize(size: Int): Builder {
            bTitleSize = size
            return this
        }

        fun text(text: String): Builder {
            bText = text
            return this
        }

        fun yesText(text: String): Builder {
            bYesText = text
            return this
        }

        fun yesTextColor(color: String): Builder {
            bYesColor = color
            return this
        }

        fun noText(text: String): Builder {
            bNoText = text
            return this
        }

        fun showOne(show: Boolean): Builder {
            bShowOneBtn = show
            return this
        }

        fun yes(yesHandler: YesHandler): Builder {
            bYesHandler = yesHandler
            return this
        }

        fun no(noHandler: NoHandler): Builder {
            bNoHandler = noHandler
            return this
        }

        fun show() {
            val dialog = NormalDialog()
            dialog.run {
                mWindowAnim = bAnim
                mWidthRatio = bWidthRatio
                mCancelableOutSide = bCancelableOutSide
                mCancelable = bCancelable
                mYesHandler = bYesHandler
                mNoHandler = bNoHandler
                mTitle = bTitle
                mTitleColor = bTitleColor
                mTitleSize = bTitleSize
                mText = bText
                mYesText = bYesText
                mYesColor = bYesColor
                mNoText = bNoText
                mShowOneBtn = bShowOneBtn
                mNoHandler=bNoHandler
                mYesHandler = bYesHandler
            }
            dialog.show(activity.supportFragmentManager, "")
        }
    }
}