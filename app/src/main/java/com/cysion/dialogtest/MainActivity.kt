package com.cysion.dialogtest

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.cysion.dialogtest.handler.MyHandler
import com.cysion.wedialog.DParams
import com.cysion.wedialog.WeDialog
import com.cysion.wedialog.listener.ViewHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_custom2.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vTvShowloading.setOnClickListener {
            WeDialog.loading(this)
        }
        vTvCloseloading.setOnClickListener {
            for (i in 1..5) {
                Thread {
                    Thread.sleep((1000 * i).toLong())
                    runOnUiThread {
                        WeDialog.loading(this)
                    }
                }.start()
                Thread {
                    Thread.sleep((1500 * i).toLong())
                    runOnUiThread {
                        WeDialog.dismiss()
                    }
                }.start()
            }
        }

        vTvShowCustom1.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom1)
                .setCancelable(false)
                .setGravity(Gravity.BOTTOM)
                .params(DParams().addParam("KEY", "click me"))
                .show(MyHandler { obj: String, flag: Int ->
                    Toast.makeText(this, "$obj", Toast.LENGTH_SHORT).show()
                })
        }

        vTvShowCustom2.setOnClickListener {
            val tname = "xiao ming"
            val token = "123"
            WeDialog.custom(this)
                .setDim(0.6f)
                .setWidthRatio(0.90f)
                .setCancelableOutSide(true)
                .layout(R.layout.dialog_custom2)
                .params(DParams().addParam("name", tname).addParam("token", token))
                .show(object : ViewHandler {
                    override fun handle(
                        dialogFragment: DialogFragment,
                        dialogView: View,
                        bundle: Bundle
                    ) {
                        dialogView.vEtName.setText(bundle.getString("name"))
                        dialogView.vEtToken.setText(bundle.getString("token"))
                        dialogView.vBtnSubmit.setOnClickListener {
                            Toast.makeText(
                                this@MainActivity, "name:${dialogView.vEtName.text}" +
                                        ";token:${dialogView.vEtToken.text}", Toast.LENGTH_SHORT
                            ).show()
                            dialogFragment.dismissAllowingStateLoss()
                        }
                    }
                })
        }
    }
}
