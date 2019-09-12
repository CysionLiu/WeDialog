package com.cysion.dialogtest

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
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
        //if you want init sth globally,you can invoke this;
        WeDialog.initOnce(WeDialog.Config().apply {
            mGravity = Gravity.CENTER
            mDimCount = 0.2f
        })
        showLoading()
        showCustom()
        showNormal()
    }


    private fun showNormal() {

        vTvShowNormal1.setOnClickListener {
            WeDialog.normal(this)
                .setMsg(getString(R.string.str_goog_job))
                .showOneBtn(true)
                .show {
                    it.dismissAllowingStateLoss()
                }
        }


        vTvShowNormal2.setOnClickListener {
            WeDialog.normal(this)
                .setTitle(getString(R.string.str_notice))
                .setMsg(getString(R.string.str_goog_job))
                .clickCancel {
                    WeDialog.loading(this@MainActivity)
                }
                .show {
                    it.dismissAllowingStateLoss()
                    Toast.makeText(this, getString(R.string.str_confirm), Toast.LENGTH_SHORT).show()
                }
        }


        vTvShowNormal3.setOnClickListener {
            WeDialog.normal(this)
                .setTitle(getString(R.string.str_notice))
                .setMsg(getString(R.string.str_goog_job))
                .setAnim(R.style.BottomDialogAnimation)
                .setCancelable(false)
                .setMsgSize(20)
                .setTitleColor("#ffff0000")
                .setYesText(getString(R.string.str_confirm))
                .setNoText(getString(R.string.str_cancel))
                .clickCancel {
                    Toast.makeText(this, getString(R.string.str_cancel), Toast.LENGTH_SHORT).show()
                }
                .show {
                    runOnUiThread {
                        WeDialog.custom(this@MainActivity)
                            .layout(R.layout.dialog_custom0)
                            .show { df, dialogView, bundle ->
                                dialogView.setOnClickListener { _ ->
                                    df.dismissAllowingStateLoss()
                                }
                            }
                    }
                }
        }
    }

    private fun showCustom() {
        vTvShowCustom1.setOnClickListener {
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom0)
                .show()
        }

        vTvShowCustom2.setOnClickListener {
            val s = "click me"
            WeDialog.custom(this)
                .layout(R.layout.dialog_custom1)
                .setAnim(R.style.dAnimation_fade)
                .show { df, dialogView, bundle ->
                    dialogView.vBtnCus1.text = s
                    dialogView.vBtnCus1.setOnClickListener {
                        Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show()
                        df.dismissAllowingStateLoss()
                    }
                }
        }

        vTvShowCustom3.setOnClickListener {
            val tname = "xiao ming"
            WeDialog.custom(this)
                .setAnim(R.style.BottomDialogAnimation)
                .layout(R.layout.dialog_custom2)
                .setDim(0.7f)
                .setXOffset(100)
                .params(WeParams().addParam("token", "123"))
                .show { df, dialogView, bundle ->
                    dialogView.vEtName.setText(tname)
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

        vTvShowCustom4.setOnClickListener {
            startActivity(Intent(this, AnchorActivity::class.java))
        }
    }

    private fun showLoading() {
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
    }
}
