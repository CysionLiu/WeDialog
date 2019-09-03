package com.cysion.wedialog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cysion.wedialog.CustomViewHandler
import com.cysion.wedialog.DParams
import com.cysion.wedialog.R

class CustomDialog : DialogFragment() {

    @LayoutRes
    private var mLayoutId: Int = 0

    private var dialogViewHandler: CustomViewHandler? = null

    private var mParams: DParams? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        savedInstanceState?.run {
            dialogViewHandler = getSerializable("listener") as CustomViewHandler?
        }
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
            dialogViewHandler?.handle(this@CustomDialog, view, mParams)
            dialog.setContentView(view)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("listener",dialogViewHandler)
    }

    class Builder(val activity: FragmentActivity) {

        private var mParams: DParams = DParams()
        private var mLayoutRes: Int = 0


        fun params(params: DParams): Builder {
            if (params == null) {
                mParams = DParams()
            } else {
                mParams = params
            }
            return this
        }

        fun layout(@LayoutRes layoutId: Int): Builder {
            mLayoutRes = layoutId
            return this
        }

        fun show(callback: CustomViewHandler) {
            if (mLayoutRes == 0) {
                throw IllegalArgumentException("wrong layoutId")
            }
            val dialog = CustomDialog()
            dialog.mParams = mParams
            dialog.mLayoutId = mLayoutRes
            dialog.dialogViewHandler = callback
            dialog.show(activity.supportFragmentManager, "123123")
        }
    }
}