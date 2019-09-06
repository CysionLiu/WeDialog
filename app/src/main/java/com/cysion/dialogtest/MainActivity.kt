package com.cysion.dialogtest

import android.os.Bundle
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
        vTvShowloading.getLocationOnScreen(IntArray(2))
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
                .setWidthRatio(0.5f)
                .setVMargin(-0.2f)
                .setHMargin(0.1f)
                .params(DParams().addParam("KEY", "click me"))
                .show(MyHandler { obj: String, flag: Int ->
                    Toast.makeText(this, "$obj", Toast.LENGTH_SHORT).show()
                })
        }

        vTvShowCustom2.setOnClickListener {
            val tname = "xiao ming"
            val token = "123"
            WeDialog.custom(this)
                .setAnim(R.style.BottomDialogAnimation)
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

        vTvShowCustom3.setOnClickListener {
            WeDialog.normal(this)
                .title("注意啦.")
                .msg("You have done a good job !")
                .clickCancel {

                }
                .show {
                    Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
                }
        }
    }

}
