package com.cysion.dialogtest.handler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.cysion.wedialog.listener.BaseViewHandler
import com.cysion.wedialog.listener.EventHander
import kotlinx.android.synthetic.main.dialog_custom1.view.*

class MyHandler(eventHander: EventHander<String>) : BaseViewHandler<String>(eventHander) {
    override fun handle(dialogFragment: DialogFragment, dialogView: View, bundle: Bundle) {
        dialogView.vBtnCus1.text = bundle.getString("KEY")
        dialogView.vBtnCus1.setOnClickListener {
            eventHander.invoke(dialogView.vTvCus1.text.toString(), 200)
            dialogFragment.dismissAllowingStateLoss()
        }
    }
}