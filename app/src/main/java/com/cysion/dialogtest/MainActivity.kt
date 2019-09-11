package com.cysion.dialogtest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cysion.wedialog.WeDialog
import com.cysion.wedialog.WeParams
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_custom1.view.*
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
                .setAnim(R.style.dAnimation_fade)
                .setDim(0.1f)
                .setWidthRatio(0.5f)
                .setYOffset(-330)
                .setXOffset(-180)
                .params(WeParams().addParam("KEY", "click me"))
                .show { df, dialogView, bundle ->
                    dialogView.vBtnCus1.text = bundle.getString("KEY")
                    dialogView.vBtnCus1.setOnClickListener {
                        Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show()
                        df.dismissAllowingStateLoss()
                    }
                }
        }

        vTvShowCustom2.setOnClickListener {
            val tname = "xiao ming"
            val token = "123"
            WeDialog.custom(this)
                .setAnim(R.style.BottomDialogAnimation)
                .layout(R.layout.dialog_custom2)
                .params(WeParams().addParam("name", tname).addParam("token", token))
                .show { df, dialogView, bundle ->
                    dialogView.vEtName.setText(bundle.getString("name"))
                    dialogView.vEtToken.setText(bundle.getString("token"))
                    dialogView.vBtnSubmit.setOnClickListener {
                        Toast.makeText(
                            this@MainActivity, "name:${dialogView.vEtName.text}" +
                                    ";token:${dialogView.vEtToken.text}", Toast.LENGTH_SHORT
                        ).show()
                        df.dismissAllowingStateLoss()
                    }
                }
        }

        vTvShowCustom3.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .setWidthRatio(0.3f)
                .anchor(vTvShowloading)
                .show { df, dialogView, bundle ->

                }

        }

        vTvShowCustom4.setOnClickListener {
            startActivity(Intent(this,AnchorActivity::class.java))
        }
    }
}
